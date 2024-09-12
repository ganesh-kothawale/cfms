package `in`.porter.cfms.servers.commons.extensions

import java.io.FileReader
import java.nio.file.Path
import java.util.*

fun Properties.loadResource(clazz: Any, file: String): Properties {
  val inputStream = clazz.javaClass.classLoader.getResourceAsStream(file)
  load(inputStream)
  return this
}

fun Properties.loadFile(vararg paths: String): Properties = this.apply {
  /* configuration path can be provided; defaults to /home/app/config */
  val path = System.getenv("CONFIG_PATH") ?: "/home/app/config"
  val filePath = Path.of(path, *paths).toString()
  FileReader(filePath).use {
    load(it)
  }
}
