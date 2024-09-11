package `in`.porter.kptr.servers.ktor.di

import `in`.porter.kptr.servers.commons.di.components.RootComponent
import `in`.porter.kptr.servers.commons.usecases.external.Run
import `in`.porter.kptr.data.di.PsqlDataComponent

import dagger.Component

@HttpScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ]
)
interface HttpComponent {
  val run: Run
}
