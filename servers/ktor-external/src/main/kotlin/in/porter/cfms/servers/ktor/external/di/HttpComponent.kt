package `in`.porter.cfms.servers.ktor.external.di

import `in`.porter.cfms.servers.commons.di.components.RootComponent
import `in`.porter.cfms.servers.commons.usecases.external.Run
import `in`.porter.cfms.data.di.PsqlDataComponent
import `in`.porter.cfms.servers.ktor.di.HttpScope

import dagger.Component
import `in`.porter.cfms.servers.ktor.external.usecases.holidays.CreateHolidaysHttpService
import `in`.porter.cfms.servers.ktor.external.usecases.holidays.DeleteHolidaysHttpService
import `in`.porter.cfms.servers.ktor.external.usecases.holidays.UpdateHolidaysHttpService
import `in`.porter.cfms.servers.ktor.external.usecases.franchises.CreateFranchiseRecordHttpService

@HttpScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ]
)
interface HttpComponent {
  val run: Run
  val createHolidaysHttpService: CreateHolidaysHttpService
  val updateHolidaysHttpService: UpdateHolidaysHttpService
  val deleteHolidaysHttpService: DeleteHolidaysHttpService
  val createFranchiseRecordHttpService : CreateFranchiseRecordHttpService
}
