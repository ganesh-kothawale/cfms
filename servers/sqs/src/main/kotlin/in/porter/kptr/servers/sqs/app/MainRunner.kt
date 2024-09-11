package `in`.porter.kptr.servers.sqs.app

import `in`.porter.kotlinutils.serde.jackson.json.JsonMapper
import `in`.porter.kptr.api.models.async.AsyncJob
import `in`.porter.kptr.servers.sqs.configs.customConfigure
import `in`.porter.kptr.servers.sqs.consumers.AsyncSQSConfig
import `in`.porter.kptr.servers.sqs.di.SQSComponentFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.kotlin.logger

private val logger = logger("in.porter.kptr.servers.sqs.app.MainRunnerKt")

suspend fun main() {
    JsonMapper.configure { customConfigure() }

    SQSComponentFactory.build().run.invoke(
        initApplication = { initApplication() },
        startApplication = { startApplication(it) },
        stopApplication = { stopApplication(it) }
    )
}

private fun initApplication(): List<Looper<AsyncJob>> {
    val sqsComponent = SQSComponentFactory.build()
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
