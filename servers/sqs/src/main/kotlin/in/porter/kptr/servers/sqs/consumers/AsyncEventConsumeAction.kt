package `in`.porter.kptr.servers.sqs.consumers

import `in`.porter.kotlinutils.commons.workerpools.stream.FixedWorkerPool
import `in`.porter.kotlinutils.sqs.SQSMessage
import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamConsumeAction
import `in`.porter.kptr.api.models.async.AsyncJob
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import javax.inject.Inject

class AsyncEventConsumeAction
@Inject
constructor(
  private val asyncEventWorkAction: AsyncEventWorkAction
) : StreamConsumeAction<AsyncJob> {

  override suspend fun invoke(
    messagesChannel: ReceiveChannel<SQSMessage<AsyncJob>>,
    receiptHandlesChannel: SendChannel<SQSMessage<AsyncJob>>
  ) {
    FixedWorkerPool(100, asyncEventWorkAction)
      .consume(messagesChannel, receiptHandlesChannel)
  }

}
