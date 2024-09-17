package `in`.porter.cfms.servers.commons.di

import `in`.porter.cfms.servers.commons.extensions.Dir
import `in`.porter.cfms.servers.commons.extensions.loadFile
import java.util.*

object ApplicationConfig {

  const val INDIA_STACK = "india"
  const val UAE_STACK = "uae"

  val Stack: String = System.getenv("INFRA_STACK")
  private val properties = Properties().loadFile(Dir.PROPERTIES,"application.properties").loadFile(Dir.PROPERTIES,"pubsub_application.properties")

  val tasksProject: String = properties.getProperty("jobs.tasks.project")

  val pubsubSubscription: String = properties.getProperty("jobs.tasks.subscription")
}
