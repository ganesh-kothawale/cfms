package `in`.porter.cfms.servers.pubsub.consumers

import `in`.porter.cfms.api.models.async.AsyncJob
import javax.inject.Inject

class AsyncJobService
@Inject
constructor(
) {

  suspend fun invoke(job: AsyncJob) {
     TODO()
  }
}
