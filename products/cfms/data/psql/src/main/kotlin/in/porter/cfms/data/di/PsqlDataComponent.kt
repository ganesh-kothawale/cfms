package `in`.porter.cfms.data.di

import dagger.BindsInstance
import dagger.Component
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import io.micrometer.core.instrument.MeterRegistry
import org.jetbrains.exposed.sql.Database

@PsqlDataScope
@Component(
  modules =
  [
    UtilsModule::class,
    HolidayModule::class
  ]
)
interface PsqlDataComponent {

  val holidayRepo : HolidayRepo

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun database(db: Database): Builder

    @BindsInstance
    fun meterRegistry(meterRegistry: MeterRegistry): Builder

    fun build(): PsqlDataComponent
  }

}
