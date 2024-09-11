package `in`.porter.kptr.servers.commons.usecases.internal

import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
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
  private val meterRegistry: MeterRegistry,
  private val sqsClient: SQSClient
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
          launch { sqsClient.close() }
        }
      }
      logger.info { "Resources shut down complete" }
    } catch (e: Exception) {
      logger.error(e) { "Failed to shut down resources" }
    }
  }

}
