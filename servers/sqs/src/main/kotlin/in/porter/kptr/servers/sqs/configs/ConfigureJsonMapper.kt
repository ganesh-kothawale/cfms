package `in`.porter.kptr.servers.sqs.configs

import `in`.porter.kotlinutils.serde.jackson.custom.DurationSerde
import `in`.porter.kotlinutils.serde.jackson.custom.InstantSerde
import `in`.porter.kotlinutils.serde.jackson.custom.MoneySerde
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.KotlinModule

fun ObjectMapper.customConfigure() {
  propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
  configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  registerModules(
    KotlinModule.Builder().build(), DurationSerde.millisModule, InstantSerde.millisModule, MoneySerde.moneyModule
  )
}
