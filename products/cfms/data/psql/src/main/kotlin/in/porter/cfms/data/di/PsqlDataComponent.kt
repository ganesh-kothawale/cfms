package `in`.porter.cfms.data.di

import dagger.BindsInstance
import dagger.Component
import `in`.porter.cfms.data.orders.di.OrderDetailsRepoModule
import `in`.porter.cfms.domain.auditlogs.repos.AuditLogRepo
import `in`.porter.cfms.domain.courierPartners.repos.CourierPartnersRepo
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.recon.repos.ReconRepo
import `in`.porter.cfms.domain.cpConnections.repos.CPConnectionRepo
import `in`.porter.cfms.domain.hlp.repos.HlpsRepo
import `in`.porter.cfms.domain.pickuptasks.PickupTasksRepo
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import io.micrometer.core.instrument.MeterRegistry
import org.jetbrains.exposed.sql.Database

@PsqlDataScope
@Component(
    modules =
    [
        UtilsModule::class,
        HolidayModule::class,
        OrderDetailsRepoModule::class,
        FranchiseReposModule::class,
        CPConnectionRepoModule::class,
        CourierPartnerRepoModule::class,
        HlpReposModule::class,
        TasksModule::class,
        AuditLogsModule::class,
        ReconModule::class,
        PickupTasksModule::class,
    ]
)
interface PsqlDataComponent {

    val holidayRepo: HolidayRepo
    val orderDetailsRepo: OrderDetailsRepo
    val cpConnectionRepo: CPConnectionRepo
    val courierPartnersRepo: CourierPartnersRepo
    val franchiseRepo: FranchiseRepo
    val hlpRepo: HlpsRepo
    val tasksRepo: TasksRepo
    val auditLogsRepo: AuditLogRepo
    val reconRepo: ReconRepo
    val pickupTasksRepo: PickupTasksRepo

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun database(db: Database): Builder

        @BindsInstance
        fun meterRegistry(meterRegistry: MeterRegistry): Builder

        fun build(): PsqlDataComponent
    }

}
