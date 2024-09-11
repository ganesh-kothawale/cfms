package `in`.porter.kptr.client.di

import `in`.porter.kotlinutils.serde.jackson.custom.*
import `in`.porter.kotlinutils.serde.jackson.json.JacksonSerdeMapperFactory
import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClientImpl
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Module
internal class UtilsModule {

  @Provides
  @ClientScope
  fun provideSQSClient(): SQSClient =
    SQSClientImpl(
      client = SqsAsyncClient.create(),
      serdeMapper = getSerdeMapper()
    )

  private fun getSerdeMapper() =
    JacksonSerdeMapperFactory().build().apply {
      this.configure {
        propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        registerModules(
          KotlinModule.Builder().build(),
          InstantSerde.millisModule,
          MoneySerde.moneyModule,
          LocalDateSerde.localDateModule,
          LocalTimeSerde.localTimeSerde,
          DurationSerde.millisModule)
      }
    }
}
