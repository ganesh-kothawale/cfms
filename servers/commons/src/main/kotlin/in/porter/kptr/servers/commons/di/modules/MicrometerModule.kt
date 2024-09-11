package `in`.porter.kptr.servers.commons.di.modules

import `in`.porter.kptr.servers.commons.extensions.loadResource
import dagger.Module
import dagger.Provides
import io.micrometer.cloudwatch2.CloudWatchConfig
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry
import io.micrometer.core.instrument.Clock
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.composite.CompositeMeterRegistry
import io.micrometer.core.instrument.logging.LoggingMeterRegistry
import io.micrometer.core.instrument.logging.LoggingRegistryConfig
import io.micrometer.core.instrument.step.StepMeterRegistry
import org.apache.logging.log4j.kotlin.Logging
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient
import java.time.Duration
import java.util.*
import javax.inject.Singleton


@Module
class MicrometerModule {
  companion object {
    private const val prefix = "meterRegistry"
    private const val logging = "$prefix.logging"
    private const val cloudwatch = "$prefix.cloudwatch"
  }

  private val config: Properties =
    Properties().loadResource(this, "micrometer.properties")

  @Provides
  @Singleton
  fun provideMeterRegistry(): MeterRegistry = CompositeMeterRegistry(
    Clock.SYSTEM,
    listOfNotNull(
      getCloudWatchMeterRegistry(),
      getLoggingMeterRegistry()
    )
  )

  private fun getCloudWatchMeterRegistry(): StepMeterRegistry? {
    if (isEnabled(cloudwatch).not()) return null

    val namespace = getValue(cloudwatch, "namespace")
    return CloudWatchMeterRegistry(
      object : CloudWatchConfig {
        override fun get(key: String): String? = null
        override fun step() = getStep(cloudwatch)
        override fun namespace() = namespace
      },
      Clock.SYSTEM,
      CloudWatchAsyncClient.create()
    )
  }

  private fun getLoggingMeterRegistry(): StepMeterRegistry? {
    if (isEnabled(logging).not()) return null

    return LoggingMeterRegistry(
      object : LoggingRegistryConfig {
        override fun get(key: String): String? = null
        override fun step() = getStep(logging)
      },
      Clock.SYSTEM
    )
  }

  private fun isEnabled(registry: String): Boolean =
    getValue(registry, "enabled").toBoolean()

  private fun getStep(registry: String) =
    getValue(registry, "step")
      .let { Duration.ofSeconds(it.toLong()) }

  private fun getValue(registry: String, key: String): String =
    config.getProperty("$registry.$key")

}
