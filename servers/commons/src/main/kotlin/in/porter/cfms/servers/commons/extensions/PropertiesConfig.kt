package `in`.porter.cfms.servers.commons.extensions

import java.util.*

fun Properties.loadResource(clazz: Any, file: String): Properties {
  val inputStream = clazz.javaClass.classLoader.getResourceAsStream(file)
  load(inputStream)
  return this
}
