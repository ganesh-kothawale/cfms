package `in`.porter.cfms.servers.commons.extensions

import java.io.FileReader
import java.util.*
import kotlin.io.path.Path

enum class Dir(val dirName: String) {
  PROPERTIES("properties"),
  SECRETS("secrets")
}

fun Properties.loadFile(dir: Dir, vararg paths: String): Properties = this.apply {
  val path = System.getenv("CONFIG_PATH")?:""
  val filePath = Path(path, dir.dirName, *paths).toString()
  FileReader(filePath).use { load(it) }
}
