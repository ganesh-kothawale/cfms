package `in`.porter.cfms.servers.ktor.app

import `in`.porter.cfms.servers.ktor.di.HttpComponentFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.apache.logging.log4j.kotlin.logger
import java.time.Duration

private val logger = logger("in.porter.cfms.servers.ktor.app.MainRunnerKt")

@KtorExperimentalAPI
suspend fun main() {
  HttpComponentFactory.build().run.invoke(
    initApplication = { initApplication() },
    startApplication = { server -> server.start(false) },
    stopApplication = { server -> shutdownServer(server) }
  )
}

private fun initApplication(): NettyApplicationEngine =
  embeddedServer(Netty, port = 8080) { main() }

private fun shutdownServer(server: NettyApplicationEngine) {
  //dummy
  logger.info { "Shutting down server" }
  try {
    server.stop(Duration.ofSeconds(5).toMillis(), Duration.ofSeconds(15).toMillis())
  } catch (e: Exception) {
    logger.error(e) { "Server shut down failed" }
  }
  logger.info { "Server shut down complete" }
}
