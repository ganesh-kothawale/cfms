package `in`.porter.cfms.servers.commons.di

import `in`.porter.cfms.servers.commons.extensions.Dir
import `in`.porter.cfms.servers.commons.extensions.loadFile
import `in`.porter.kotlinutils.commons.config.Environment
import io.ktor.http.*
import java.util.*

object ApplicationConfig {

  const val INDIA_STACK = "india"
  const val UAE_STACK = "uae"

  val Stack: String = System.getenv("INFRA_STACK")
  private val properties = Properties().loadFile(Dir.PROPERTIES,"application.properties")
  private val hosts = Properties().loadFile(Dir.PROPERTIES,"hosts.properties")

  val env: Environment = Environment.valueOf(properties.getProperty("env"))
  val queueUrl: String = properties.getProperty("queue_url")
  val redisUrl: String = properties.getProperty("redis_url")

  val externalHost: String = properties.getProperty("external.host")
  val externalPort: Int = properties.getProperty("external.port").toInt()
  val externalProtocol: URLProtocol = URLProtocol(properties.getProperty("external.protocol"), externalPort)

  val jobsProvider: String = properties.getProperty("jobs.provider", "sqs")

  val tasksProject: String = properties.getProperty("jobs.tasks.project")
  val tasksLocation: String = properties.getProperty("jobs.tasks.location")
  val tasksQueue: String = properties.getProperty("jobs.tasks.queue")
  val tasksProjectEmail: String = properties.getProperty("jobs.tasks.service_account_mail")

  val pubsubTopic: String = properties.getProperty("jobs.pubsub.topic")

  val pubsubSubscription: String = properties.getProperty("jobs.pubsub.subscription")

  val pfbQueueUrl: String = hosts.getProperty("pfb.queueUrl")
  val pfbPubsubTopic: String = hosts.getProperty("pfb.topic")

  val vpQueueUrl: String = hosts.getProperty("vp.queueUrl")
  val vpPubSubTopic: String = hosts.getProperty("vp.topic")
}
