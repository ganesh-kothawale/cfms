package `in`.porter.cfms.client.clients

import `in`.porter.cfms.api.models.async.AsyncJob
import javax.inject.Inject

class CfmsHttpClient
@Inject
constructor() : CfmsClient {

  override suspend fun publishJob(job: AsyncJob) {
    TODO()
  }

}
