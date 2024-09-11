package `in`.porter.kptr.servers.commons.di.factories

import `in`.porter.kptr.data.di.DaggerPsqlDataComponent
import `in`.porter.kptr.servers.commons.di.components.RootComponent
import `in`.porter.kptr.data.di.PsqlDataComponent

object PsqlDataComponentFactory {

  fun build(rootComponent: RootComponent): PsqlDataComponent =
    DaggerPsqlDataComponent.builder()
      .database(rootComponent.database)
      .meterRegistry(rootComponent.meterRegistry)
      .build()

}
