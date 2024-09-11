package `in`.porter.cfms.servers.sqs.di

import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamQueueDrainer
import `in`.porter.cfms.api.models.async.AsyncJob
import `in`.porter.cfms.data.di.PsqlDataComponent
import `in`.porter.cfms.servers.commons.di.components.RootComponent
import `in`.porter.cfms.servers.commons.usecases.external.Run
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
