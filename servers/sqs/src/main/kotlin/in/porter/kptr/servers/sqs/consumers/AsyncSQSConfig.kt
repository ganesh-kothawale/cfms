package `in`.porter.kptr.servers.sqs.consumers

import `in`.porter.kptr.servers.sqs.configs.SQSDrainerConfig
import `in`.porter.kptr.servers.sqs.configs.loadResource
import java.time.Duration
import java.util.*

object AsyncSQSConfig {
  private val properties: Properties =
    Properties().loadResource(this::javaClass, "sqs_application.properties")


  val sqsConfig = SQSDrainerConfig(
    queueUrl = properties.getProperty("sqs.queueUrl"),
    fetcherConfig = SQSDrainerConfig.FetcherConfig(
      maxPolls = properties.getProperty("sqs.fetcher.maxPolls").toInt(),
      minLoopMessagesCnt = properties.getProperty("sqs.fetcher.minLoopMessagesCnt").toInt()
    ),
    deleterConfig = SQSDrainerConfig.DeleterConfig(
      deleteMaxDelay = Duration.ofSeconds(properties.getProperty("sqs.deleter.deleteMaxDelaySeconds").toLong())
    ),
    looperConfig = SQSDrainerConfig.LooperConfig(
      pauseDuration = Duration.ofSeconds(properties.getProperty("sqs.looper.pauseSeconds").toLong()),
      pauseWindowMultiplier = properties.getProperty("sqs.looper.pauseWindowMultiplier")?.toDouble()
    )
  )
}
