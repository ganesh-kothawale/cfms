package `in`.porter.cfms.servers.commons.di.factories

import  `in`.porter.cfms.servers.commons.di.components.DaggerRootComponent

object ComponentsFactory {

  val rootComponent = DaggerRootComponent.create()
  val psqlDataComponent = PsqlDataComponentFactory.build(rootComponent)

}
