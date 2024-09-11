package `in`.porter.kptr.servers.sqs.consumers

import `in`.porter.kptr.api.models.async.AsyncJob
import javax.inject.Inject

class AsyncJobService
@Inject
constructor(
) {

  suspend fun invoke(job: AsyncJob) {
     TODO()
  }
}
