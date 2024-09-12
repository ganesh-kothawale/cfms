package `in`.porter.cfms.servers.pubsub.configs

import java.util.*

fun Properties.loadResource(clazz: Any, file: String): Properties {
  val inputStream = clazz.javaClass.classLoader.getResourceAsStream(file)
  load(inputStream)
  return this
}
