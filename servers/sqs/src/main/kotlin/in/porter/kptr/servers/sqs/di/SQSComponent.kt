package `in`.porter.kptr.servers.sqs.di

import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamQueueDrainer
import `in`.porter.kptr.api.models.async.AsyncJob
import `in`.porter.kptr.data.di.PsqlDataComponent
import `in`.porter.kptr.servers.commons.di.components.RootComponent
import `in`.porter.kptr.servers.commons.usecases.external.Run
import dagger.Component

@SQSScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ],
  modules = [
    SQSModule::class
  ]
)
interface SQSComponent {
  val run: Run
  val drainer: StreamQueueDrainer<AsyncJob>
}
