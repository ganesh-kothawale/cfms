package `in`.porter.cfms.servers.pubsub.app

import `in`.porter.kotlinutils.serde.jackson.json.JsonMapper
import `in`.porter.cfms.api.models.async.AsyncJob
import `in`.porter.cfms.servers.pubsub.configs.customConfigure
import `in`.porter.cfms.servers.pubsub.consumers.AsyncSQSConfig
import `in`.porter.cfms.servers.pubsub.di.PubSubComponentFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.kotlin.logger

private val logger = logger("in.porter.cfms.servers.sqs.app.MainRunnerKt")

suspend fun main() {
    JsonMapper.configure { customConfigure() }

    PubSubComponentFactory.build().run.invoke(
        initApplication = { initApplication() },
        startApplication = { startApplication(it) },
        stopApplication = { stopApplication(it) }
    )
}

private fun initApplication(): List<Looper<AsyncJob>> {
    val sqsComponent = PubSubComponentFactory.build()
    val looper = Looper(AsyncSQSConfig.sqsConfig.queueUrl, sqsComponent.drainer, AsyncSQSConfig.sqsConfig.looperConfig)
    return listOf(looper)
}

private suspend fun startApplication(loopers: List<Looper<AsyncJob>>) {
    withContext(Dispatchers.Default) {
        loopers.forEach { looper ->
            launch { looper.start() }
        }
        logger.info { "Loopers started" }
    }
}

private fun stopApplication(loopers: List<Looper<AsyncJob>>) {
    loopers.forEach { it.shutdown() }
}
