package `in`.porter.cfms.client.clients

import `in`.porter.cfms.api.models.async.AsyncJob


interface CfmsClient {
  suspend fun publishJob(job: AsyncJob)
}
