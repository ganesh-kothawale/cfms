package `in`.porter.kptr.servers.commons.di.components

import `in`.porter.kotlinutils.commons.config.Environment
import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
import `in`.porter.kptr.servers.commons.di.modules.MicrometerModule
import `in`.porter.kptr.servers.commons.di.modules.PsqlModule
import `in`.porter.kptr.servers.commons.di.modules.SQSClientModule
import `in`.porter.kptr.servers.commons.di.modules.UtilsModule
import com.zaxxer.hikari.HikariDataSource
import dagger.Component
import io.ktor.client.*
import io.micrometer.core.instrument.MeterRegistry
import org.jetbrains.exposed.sql.Database
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    PsqlModule::class,
    SQSClientModule::class,
    MicrometerModule::class,
    UtilsModule::class
  ]
)
interface RootComponent {
  val database: Database
  val hikariDataSource: HikariDataSource
  val meterRegistry: MeterRegistry
  val sqsClient: SQSClient
  val httpClient: HttpClient
  val environment: Environment
}
