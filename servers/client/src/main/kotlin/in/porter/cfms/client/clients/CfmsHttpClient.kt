package `in`.porter.cfms.client.clients

import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
import `in`.porter.cfms.api.models.async.AsyncJob
import javax.inject.Inject

class CfmsHttpClient
@Inject
constructor(
  private val client: SQSClient
) : CfmsClient {

  override suspend fun publishJob(job: AsyncJob) {
    TODO()
  }

}
