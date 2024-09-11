package `in`.porter.kptr.servers.commons.usecases.external

import `in`.porter.kptr.servers.commons.usecases.internal.Init
import `in`.porter.kptr.servers.commons.usecases.internal.Shutdown
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.kotlin.Logging
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class Run
@Inject
constructor(
  private val init: Init,
  private val shutdown: Shutdown
) {

  companion object : Logging

  suspend fun <A> invoke(
    initApplication: suspend () -> A,
    startApplication: suspend (A) -> Unit,
    stopApplication: (A) -> Unit
  ) {
    val latch = CountDownLatch(1)
    init.invoke()
    val application = initApplication.invoke()

    Runtime.getRuntime().addShutdownHook(Thread {
      stopApplication.invoke(application)
      shutdown.invoke()
      latch.countDown()
    })

    startApplication.invoke(application)

    logger.info { "Server started" }
    latch.await()

    LogManager.shutdown()
  }

}
