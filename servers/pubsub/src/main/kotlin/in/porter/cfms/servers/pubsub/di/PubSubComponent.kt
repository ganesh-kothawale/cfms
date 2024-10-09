package `in`.porter.cfms.servers.pubsub.di

import `in`.porter.cfms.data.di.PsqlDataComponent
import `in`.porter.cfms.servers.commons.di.components.RootComponent
import `in`.porter.cfms.servers.commons.usecases.external.Run
import dagger.Component
import `in`.porter.gcu.pubsub.subscriber.Subscriber

@PubSubScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ],
  modules = [
    PubSubModule::class
  ]
)
interface PubSubComponent {
  val run: Run
  val subscriber: Subscriber
}
