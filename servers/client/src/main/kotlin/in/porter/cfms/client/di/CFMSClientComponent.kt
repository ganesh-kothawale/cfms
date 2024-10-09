package `in`.porter.cfms.client.di

import `in`.porter.cfms.client.clients.CfmsClient
import `in`.porter.cfms.client.config.ClientConfig
import dagger.BindsInstance
import dagger.Component

@ClientScope
@Component(
  modules = [
    ClientModule::class,
    UtilsModule::class
  ]
)
interface CFMSClientComponent {
  val cfmsClient: CfmsClient

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun config(config: ClientConfig): Builder

    fun build(): CFMSClientComponent
  }
}
