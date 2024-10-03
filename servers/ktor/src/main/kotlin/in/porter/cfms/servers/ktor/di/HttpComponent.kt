package `in`.porter.cfms.servers.ktor.di

import `in`.porter.cfms.servers.commons.di.components.RootComponent
import `in`.porter.cfms.servers.commons.usecases.external.Run
import `in`.porter.cfms.data.di.PsqlDataComponent

import dagger.Component
import `in`.porter.cfms.servers.ktor.usecases.orders.FetchOrdersHTTPService
import `in`.porter.cfms.servers.ktor.usecases.orders.CreateOrderHTTPService
import `in`.porter.cfms.servers.ktor.usecases.orders.UpdateOrderStatusHTTPService
import `in`.porter.cfms.servers.ktor.usecases.CreateCourierPartnerHttpService
import `in`.porter.cfms.servers.ktor.usecases.FetchCourierPartnerHttpService

@HttpScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ]
)
interface HttpComponent {
  val run: Run


  val createCourierPartnerHttpService: CreateCourierPartnerHttpService
  val fetchCourierPartnerHttpService: FetchCourierPartnerHttpService
  val createOrderHTTPService: CreateOrderHTTPService
  val fetchOrdersHTTPService: FetchOrdersHTTPService
  val updateOrderStatusHTTPService: UpdateOrderStatusHTTPService
}