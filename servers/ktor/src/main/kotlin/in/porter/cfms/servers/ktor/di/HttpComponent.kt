package `in`.porter.cfms.servers.ktor.di

import `in`.porter.cfms.servers.commons.di.components.RootComponent
import `in`.porter.cfms.servers.commons.usecases.external.Run
import `in`.porter.cfms.data.di.PsqlDataComponent

import dagger.Component
import `in`.porter.cfms.servers.ktor.usecases.hlp.RecordHlpDetailsHttpService
import `in`.porter.cfms.servers.ktor.usecases.orders.FetchOrdersHTTPService
import `in`.porter.cfms.servers.ktor.usecases.orders.CreateOrderHTTPService
import `in`.porter.cfms.servers.ktor.usecases.orders.UpdateOrderStatusHTTPService
import `in`.porter.cfms.servers.ktor.usecases.RecordCPConnectionHttpService
import `in`.porter.cfms.servers.ktor.usecases.FetchCPConnectionHttpService

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
}
