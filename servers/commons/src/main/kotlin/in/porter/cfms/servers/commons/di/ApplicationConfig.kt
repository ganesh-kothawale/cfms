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

  val env: Environment = Environment.valueOf(properties.getProperty("env"))

  val tasksProject: String = properties.getProperty("jobs.tasks.project")

  val pubsubSubscription: String = properties.getProperty("jobs.pubsub.subscription")
}
