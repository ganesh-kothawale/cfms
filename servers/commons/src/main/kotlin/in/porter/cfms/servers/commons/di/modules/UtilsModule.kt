package `in`.porter.cfms.servers.commons.di.modules

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import `in`.porter.kotlinutils.commons.config.Environment
import `in`.porter.kotlinutils.serde.jackson.custom.EitherSerde
import `in`.porter.kotlinutils.serde.jackson.custom.InstantSerde
import `in`.porter.kotlinutils.serde.jackson.custom.MoneySerde
import `in`.porter.kotlinutils.serde.jackson.custom.UrlSerde
import `in`.porter.kotlinutils.webclient.ktor.features.CountryClientFeature
import `in`.porter.kotlinutils.webclient.ktor.features.LanguageClientFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import `in`.porter.cfms.servers.commons.extensions.Dir
import `in`.porter.cfms.servers.commons.extensions.loadFile
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.jackson.*
import io.ktor.client.plugins.contentnegotiation.*
import java.util.*
import javax.inject.Singleton

@Module
class UtilsModule {

  companion object

  @Provides
  @Singleton
  fun provideHttpClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
      jackson {
        propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        registerModules(
          KotlinModule.Builder().build(),
          EitherSerde.module,
          InstantSerde.millisModule,
          UrlSerde.urlModule,
          MoneySerde.moneyModule)
      }
    }
    install(CountryClientFeature)
    install(LanguageClientFeature)
  }

  @Provides
  @Singleton
  fun provideEnvironment(): Environment =
    Properties().loadFile(Dir.PROPERTIES, "application.properties")
      .getProperty("env")
      .let { Environment.valueOf(it) }
}
