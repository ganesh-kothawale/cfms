package `in`.porter.cfms.servers.pubsub.di

import `in`.porter.cfms.servers.commons.di.factories.ComponentsFactory

object PubSubComponentFactory {

  fun build(): PubSubComponent =
    DaggerPubSubComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .build()
}
