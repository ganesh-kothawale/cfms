package `in`.porter.cfms.data.di

import dagger.BindsInstance
import dagger.Component
import `in`.porter.cfms.data.orders.di.OrderDetailsRepoModule
import `in`.porter.cfms.domain.auditlogs.repos.AuditLogRepo
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.recon.repos.ReconRepo
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
        TasksModule::class,
        ReconModule::class,
        AuditLogsModule::class
    ]
)
interface PsqlDataComponent {

    val holidayRepo: HolidayRepo
    val orderDetailsRepo: OrderDetailsRepo
    val franchiseRepo: FranchiseRepo
    val tasksRepo: TasksRepo
    val reconRepo: ReconRepo
    val auditLogsRepo: AuditLogRepo

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun database(db: Database): Builder

        @BindsInstance
        fun meterRegistry(meterRegistry: MeterRegistry): Builder

        fun build(): PsqlDataComponent
    }

}
