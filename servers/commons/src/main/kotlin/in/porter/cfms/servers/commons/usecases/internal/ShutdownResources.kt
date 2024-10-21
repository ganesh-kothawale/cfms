package `in`.porter.cfms.servers.commons.usecases.internal

import com.zaxxer.hikari.HikariDataSource
import io.ktor.client.*
import io.micrometer.core.instrument.MeterRegistry
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

internal class ShutdownResources
@Inject
constructor(
  private val hikariDataSource: HikariDataSource,
  private val httpClient: HttpClient,
  private val meterRegistry: MeterRegistry
) {

  companion object : Logging

  fun invoke() {
    logger.info { "Shutting down resources" }
    try {
      runBlocking {
        supervisorScope {
          launch { hikariDataSource.close() }
          launch { httpClient.close() }
          launch { meterRegistry.close() }
        }
      }
      logger.info { "Resources shut down complete" }
    } catch (e: Exception) {
      logger.error(e) { "Failed to shut down resources" }
    }
  }

}
