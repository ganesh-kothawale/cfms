package `in`.porter.kptr.client.clients

import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
import `in`.porter.kptr.api.models.async.AsyncJob
import javax.inject.Inject

class KptrHttpClient
@Inject
constructor(
  private val client: SQSClient
) : KptrClient {

  override suspend fun publishJob(job: AsyncJob) {
    TODO()
  }

}
