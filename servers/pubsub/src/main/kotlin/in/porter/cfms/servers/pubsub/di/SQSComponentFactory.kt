package `in`.porter.cfms.servers.pubsub.di

import `in`.porter.cfms.servers.commons.di.factories.ComponentsFactory
import `in`.porter.cfms.servers.sqs.di.DaggerSQSComponent

object SQSComponentFactory {

  fun build(): SQSComponent =
    DaggerSQSComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .build()
}
