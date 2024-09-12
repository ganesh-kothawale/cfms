package `in`.porter.cfms.servers.sqs.di

import `in`.porter.cfms.servers.commons.di.factories.ComponentsFactory

object SQSComponentFactory {

  fun build(): SQSComponent =
    DaggerSQSComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .build()
}
