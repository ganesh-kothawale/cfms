package `in`.porter.kptr.servers.commons.di.modules

import `in`.porter.kptr.servers.commons.extensions.loadResource
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dagger.Module
import dagger.Provides
import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.DEFAULT_REPETITION_ATTEMPTS
import org.jetbrains.exposed.sql.transactions.IGNORE_ISOLATION_LEVEL
import org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManager
import java.util.*
import javax.inject.Singleton

@Module
class PsqlModule {

  @Provides
  @Singleton
  fun provideHikariDataSource(meterRegistry: MeterRegistry): HikariDataSource {
    meterRegistry.config().meterFilter(
      object : MeterFilter {
        private val distributionMetrics = setOf(
          "hikaricp.connections.acquire",
          "hikaricp.connections.usage"
        )

        override fun configure(id: Meter.Id, config: DistributionStatisticConfig): DistributionStatisticConfig? {
          if (distributionMetrics.none { id.name.startsWith(it) }) return config

          return DistributionStatisticConfig.builder()
            .percentiles(0.5, 0.95, 0.99)
            .build().merge(config)
        }
      }
    )


    val properties = Properties().loadResource(this, "psql.properties")
      .loadResource(this, "psql_secrets.properties")

    val hikariConfig = HikariConfig(properties).apply {
      poolName = properties.getProperty("poolName")
      metricRegistry = meterRegistry

      // https://github.com/brettwooldridge/HikariCP/wiki/Rapid-Recovery
      addDataSourceProperty("socketTimeout", 15)
    }
    return HikariDataSource(hikariConfig)
  }

  @Provides
  @Singleton
  fun provideDB(dataSource: HikariDataSource): Database =
    Database.connect(
      datasource = dataSource,
      manager = {
        ThreadLocalTransactionManager(it, IGNORE_ISOLATION_LEVEL, DEFAULT_REPETITION_ATTEMPTS)
      }
    )

}
