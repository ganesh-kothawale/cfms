package `in`.porter.kptr.servers.commons.usecases.internal

import javax.inject.Inject

class Shutdown
@Inject
internal constructor(
  private val shutdownResources: ShutdownResources
) {

  fun invoke() {
    shutdownResources.invoke()
  }

}
