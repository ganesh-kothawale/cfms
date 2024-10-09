package `in`.porter.cfms.servers.pubsub.consumers

import com.google.pubsub.v1.PubsubMessage
import `in`.porter.cfms.api.models.async.AsyncJob
import `in`.porter.gcu.pubsub.subscriber.Handler
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class AsyncJobService
@Inject
constructor(
) : Handler, Traceable {

  suspend fun invoke(job: AsyncJob) : Handler {
     TODO()
  }

    override suspend fun process(message: PubsubMessage) {
        TODO("Not yet implemented")
    }
}
