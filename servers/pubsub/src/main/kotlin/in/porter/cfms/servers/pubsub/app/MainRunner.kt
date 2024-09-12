package `in`.porter.cfms.servers.pubsub.app

import `in`.porter.gcu.pubsub.subscriber.Subscriber
import `in`.porter.kotlinutils.serde.jackson.json.JsonMapper
import `in`.porter.cfms.servers.pubsub.configs.customConfigure
import `in`.porter.cfms.servers.pubsub.di.PubSubComponentFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.logger

private val logger = logger("in.porter.cfms.servers.sqs.app.MainRunnerKt")

suspend fun main() {
    JsonMapper.configure { customConfigure() }
    val pubSubComponent = PubSubComponentFactory.build()

    val subscribers = listOf(pubSubComponent.subscriber)

    pubSubComponent.run.invoke(
        initApplication = { subscribers },
        startApplication = { startSubscriber(it) },
        stopApplication = { stopSubscriber(it) }
    )
}

private suspend fun startSubscriber(subscribers: List<Subscriber>) = coroutineScope {
    subscribers.forEach { subscriber ->
        launch { subscriber.start() }
    }
    logger.info { "Subscribers started" }
}

private fun stopSubscriber(subscriber: List<Subscriber>) {
    subscriber.forEach { it.close() }
}
