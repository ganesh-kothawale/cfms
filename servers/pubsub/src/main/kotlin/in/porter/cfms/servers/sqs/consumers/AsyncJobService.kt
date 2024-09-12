package `in`.porter.cfms.servers.sqs.consumers

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
