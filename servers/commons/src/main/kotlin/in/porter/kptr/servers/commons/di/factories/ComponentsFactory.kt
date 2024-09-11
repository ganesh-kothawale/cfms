package `in`.porter.kptr.servers.commons.di.factories

import  `in`.porter.kptr.servers.commons.di.components.DaggerRootComponent

object ComponentsFactory {

  val rootComponent = DaggerRootComponent.create()
  val psqlDataComponent = PsqlDataComponentFactory.build(rootComponent)

}
