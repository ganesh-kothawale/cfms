package `in`.porter.cfms.client.di

import `in`.porter.kotlinutils.serde.jackson.custom.*
import `in`.porter.kotlinutils.serde.jackson.json.JacksonSerdeMapperFactory
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module

@Module
internal class UtilsModule {

    private fun getSerdeMapper() = JacksonSerdeMapperFactory().build().apply {
        this.configure {
            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
            registerModules(
                KotlinModule.Builder().build(),
                InstantSerde.millisModule,
                MoneySerde.moneyModule,
                LocalDateSerde.localDateModule,
                LocalTimeSerde.localTimeSerde,
                DurationSerde.millisModule
            )
        }
    }
}
