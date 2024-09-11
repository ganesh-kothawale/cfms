package `in`.porter.cfms.servers.commons.di.factories

import `in`.porter.cfms.data.di.DaggerPsqlDataComponent
import `in`.porter.cfms.servers.commons.di.components.RootComponent
import `in`.porter.cfms.data.di.PsqlDataComponent

object PsqlDataComponentFactory {

  fun build(rootComponent: RootComponent): PsqlDataComponent =
    DaggerPsqlDataComponent.builder()
      .database(rootComponent.database)
      .meterRegistry(rootComponent.meterRegistry)
      .build()

}
