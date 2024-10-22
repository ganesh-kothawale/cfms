package `in`.porter.cfms.servers.ktor.di

import `in`.porter.cfms.servers.commons.di.components.RootComponent
import `in`.porter.cfms.servers.commons.usecases.external.Run
import `in`.porter.cfms.data.di.PsqlDataComponent

import dagger.Component
import `in`.porter.cfms.servers.ktor.usecases.franchises.CreateFranchiseRecordHttpService
import `in`.porter.cfms.servers.ktor.usecases.franchises.ListFranchisesHttpService
import `in`.porter.cfms.servers.ktor.usecases.franchises.UpdateFranchiseRecordHttpService
import `in`.porter.cfms.servers.ktor.usecases.holidays.CreateHolidaysHttpService
import `in`.porter.cfms.servers.ktor.usecases.holidays.DeleteHolidaysHttpService
import `in`.porter.cfms.servers.ktor.usecases.holidays.ListHolidaysHttpService
import `in`.porter.cfms.servers.ktor.usecases.holidays.UpdateHolidaysHttpService
import `in`.porter.cfms.servers.ktor.usecases.recon.ListReconHttpService
import `in`.porter.cfms.servers.ktor.usecases.tasks.ListTasksHttpService
import `in`.porter.cfms.servers.ktor.usecases.tasks.UpdateTasksStatusHttpService
import `in`.porter.cfms.servers.ktor.usecases.hlp.FetchHlpRecordsHttpService
import `in`.porter.cfms.servers.ktor.usecases.hlp.RecordHlpDetailsHttpService
import `in`.porter.cfms.servers.ktor.usecases.hlp.UpdateHlpDetailsHttpService
import `in`.porter.cfms.servers.ktor.usecases.orders.FetchOrdersHTTPService
import `in`.porter.cfms.servers.ktor.usecases.orders.CreateOrderHTTPService
import `in`.porter.cfms.servers.ktor.usecases.orders.UpdateOrderStatusHTTPService
import `in`.porter.cfms.servers.ktor.usecases.pickuptasks.FetchPickupTasksHttpService
import `in`.porter.cfms.servers.ktor.usecases.cpConnection.RecordCPConnectionHttpService
import `in`.porter.cfms.servers.ktor.usecases.cpConnection.FetchCPConnectionHttpService

@HttpScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ]
)
interface HttpComponent {
  val run: Run
  val recordCPConnectionHttpService: RecordCPConnectionHttpService
  val fetchCPConnectionsHttpService: FetchCPConnectionHttpService
  val createOrderHTTPService: CreateOrderHTTPService
  val fetchOrdersHTTPService: FetchOrdersHTTPService
  val updateOrderStatusHTTPService: UpdateOrderStatusHTTPService
  val recordHlpDetailsHttpService: RecordHlpDetailsHttpService
  val updateHlpDetailsHttpService: UpdateHlpDetailsHttpService
  val fetchHlpRecordsHttpService: FetchHlpRecordsHttpService
  val fetchPickupTasksHttpService: FetchPickupTasksHttpService
  val createHolidaysHttpService: CreateHolidaysHttpService
  val updateHolidaysHttpService: UpdateHolidaysHttpService
  val deleteHolidaysHttpService: DeleteHolidaysHttpService
  val listHolidaysHttpService : ListHolidaysHttpService
  val createFranchiseRecordHttpService : CreateFranchiseRecordHttpService
  val listFranchisesHttpService : ListFranchisesHttpService
  val listTasksHttpService : ListTasksHttpService
  val updateTasksStatusHttpService : UpdateTasksStatusHttpService
  val updateFranchiseRecordHttpService : UpdateFranchiseRecordHttpService
  val listReconHttpService : ListReconHttpService
}
