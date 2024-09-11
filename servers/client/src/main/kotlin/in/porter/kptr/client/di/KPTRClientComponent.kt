package `in`.porter.kptr.client.di

import `in`.porter.kptr.client.clients.KptrClient
import `in`.porter.kptr.client.config.ClientConfig
import dagger.BindsInstance
import dagger.Component

@ClientScope
@Component(
  modules = [
    ClientModule::class,
    UtilsModule::class
  ]
)
interface KPTRClientComponent {
  val kptrClient: KptrClient

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun config(config: ClientConfig): Builder

    fun build(): KPTRClientComponent
  }
}
