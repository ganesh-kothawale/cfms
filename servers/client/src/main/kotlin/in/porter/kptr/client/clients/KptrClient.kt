package `in`.porter.kptr.client.clients

import `in`.porter.kptr.api.models.async.AsyncJob


interface KptrClient {
  suspend fun publishJob(job: AsyncJob)
}
