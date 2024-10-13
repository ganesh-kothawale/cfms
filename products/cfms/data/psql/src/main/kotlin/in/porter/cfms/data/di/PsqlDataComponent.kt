package `in`.porter.cfms.data.di

import dagger.BindsInstance
import dagger.Component
import `in`.porter.cfms.data.orders.di.OrderDetailsRepoModule
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import io.micrometer.core.instrument.MeterRegistry
import org.jetbrains.exposed.sql.Database

@PsqlDataScope
@Component(
  modules =
  [
    UtilsModule::class,
    HolidayModule::class,
    OrderDetailsRepoModule::class,
    FranchiseReposModule::class
    CourierPartnerRepoModule::class
  ]
)
interface PsqlDataComponent {

  val holidayRepo : HolidayRepo
  val orderDetailsRepo: OrderDetailsRepo
  val courierPartnerRepo: CourierPartnerRepo
  val franchiseRepo: FranchiseRepo

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun database(db: Database): Builder

    @BindsInstance
    fun meterRegistry(meterRegistry: MeterRegistry): Builder

    fun build(): PsqlDataComponent
  }

}
