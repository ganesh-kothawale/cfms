package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.pickuptasks.repos.PsqlPickupTasksRepo
import `in`.porter.cfms.domain.pickuptasks.repos.PickupTasksRepo
@Module
abstract class PickupTasksModule {
    @Binds
    abstract fun bindPickupTasksRepo(psqlPickupTasksRepo: PsqlPickupTasksRepo): PickupTasksRepo
}