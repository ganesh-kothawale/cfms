package `in`.porter.cfms.servers.sqs.di

import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamQueueDrainer
import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamQueueDrainerConfig
import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamQueueDrainerFactory
import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.delete.StreamMessagesDeleterImpl
import `in`.porter.cfms.api.models.async.AsyncJob
import `in`.porter.cfms.servers.sqs.consumers.AsyncEventConsumeAction
import `in`.porter.cfms.servers.sqs.consumers.AsyncSQSConfig
import dagger.Module
import dagger.Provides

@Module
object SQSModule {

  @Provides
  fun drainer(sqsClient: SQSClient, consumeAction: AsyncEventConsumeAction): StreamQueueDrainer<AsyncJob> {
    val drainerFactory = StreamQueueDrainerFactory(sqsClient)
    val config = StreamQueueDrainerConfig(
      queueUrl = AsyncSQSConfig.sqsConfig.queueUrl,
      fetcherConfig = StreamQueueDrainerConfig.FetcherConfig(
        clazz = AsyncJob::class.java,
        maxPolls = AsyncSQSConfig.sqsConfig.fetcherConfig.maxPolls,
        minLoopMessagesCnt = AsyncSQSConfig.sqsConfig.fetcherConfig.minLoopMessagesCnt
      ),
      deleterConfig = StreamQueueDrainerConfig.DeleterConfig(
        impl = StreamMessagesDeleterImpl.Batch,
        deleteMaxDelay = AsyncSQSConfig.sqsConfig.deleterConfig.deleteMaxDelay
      )
    )

    return drainerFactory.create(config, consumeAction)
  }

}
