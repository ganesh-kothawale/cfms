package `in`.porter.kptr.servers.sqs.di

import `in`.porter.kptr.servers.commons.di.factories.ComponentsFactory

object SQSComponentFactory {

  fun build(): SQSComponent =
    DaggerSQSComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .build()
}
