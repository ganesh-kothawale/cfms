package `in`.porter.kptr.servers.sqs.app

import `in`.porter.kotlinutils.commons.extensions.random
import `in`.porter.kotlinutils.commons.logging.logObjId
import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamQueueDrainer
import `in`.porter.kptr.servers.sqs.configs.SQSDrainerConfig.LooperConfig
import kotlinx.coroutines.time.delay
import org.apache.logging.log4j.kotlin.Logging
import java.time.Duration

class Looper<T>
constructor(
  queueUrl: String,
  private val drainer: StreamQueueDrainer<T>,
  private val looperConfig: LooperConfig
) {

  companion object : Logging

  private var isShutdown = false
  private val logPrefix = "Looper $logObjId for $queueUrl"

  private val maxPauseDurationWindow = getPauseDurationWindow()
  private fun getPauseDurationWindow(): ClosedRange<Duration> {
    val pauseWindowMultiplier = looperConfig.pauseWindowMultiplier ?: 1.0
    val pauseMillis = looperConfig.pauseDuration.toMillis()
    val maxPauseMillis = (pauseMillis * pauseWindowMultiplier).toLong()
    val maxPauseDuration = Duration.ofMillis(maxPauseMillis)
    return looperConfig.pauseDuration..maxPauseDuration
  }

  suspend fun start() {
    do {
      logger.info { "$logPrefix initiating drain" }
      drainer.drain()

      val randomDelay = maxPauseDurationWindow.random()
      logger.info { "$logPrefix completed drain, pausing for ${randomDelay.toMillis()} ms" }
      delay(randomDelay)
    } while (!isShutdown)

    logger.info { "$logPrefix has finished" }
  }

  fun shutdown() {
    isShutdown = true
    drainer.shutdown()
  }

}
