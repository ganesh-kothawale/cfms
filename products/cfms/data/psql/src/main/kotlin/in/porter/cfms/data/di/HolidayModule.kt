package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.holidays.repos.PsqlHolidayRepo
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo

@Module
abstract class HolidayModule {

    @Binds
    abstract fun bindHolidayRepo(psqlHolidayRepo: PsqlHolidayRepo): HolidayRepo
}
