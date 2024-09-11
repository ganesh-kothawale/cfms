package `in`.porter.kptr.servers.commons.di.modules

import `in`.porter.kotlinutils.serde.jackson.custom.DurationSerde
import `in`.porter.kotlinutils.serde.jackson.custom.InstantSerde
import `in`.porter.kotlinutils.serde.jackson.custom.ZonedDateTimeMillisSerde
import `in`.porter.kotlinutils.serde.jackson.json.JacksonSerdeMapperFactory
import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClientImpl
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import javax.inject.Singleton

@Module
class SQSClientModule {

  @Provides
  @Singleton
  fun provideSQSClient(): SQSClient =
    SQSClientImpl(
      client = SqsAsyncClient.create(),
      serdeMapper = getSerdeMapper()
    )

  private fun getSerdeMapper() =
    JacksonSerdeMapperFactory().build().apply {
      this.configure {
        configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        registerModules(
          KotlinModule.Builder().build(),
          DurationSerde.millisModule,
          ZonedDateTimeMillisSerde.epochMillisModule,
          InstantSerde.millisModule
        )
      }
    }

}
