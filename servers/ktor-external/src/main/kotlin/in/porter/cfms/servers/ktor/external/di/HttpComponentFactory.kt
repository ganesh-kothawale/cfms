package `in`.porter.cfms.servers.ktor.external.di

import `in`.porter.cfms.servers.commons.di.factories.ComponentsFactory

object HttpComponentFactory {

  fun build(): HttpComponent =
    DaggerHttpComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .build()

}
