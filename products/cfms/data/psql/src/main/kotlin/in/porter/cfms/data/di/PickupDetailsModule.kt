package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.pickuptasks.repos.PsqlPickupDetailsRepo
import `in`.porter.cfms.domain.pickuptasks.repos.PickupDetailsRepo

@Module
abstract class PickupDetailsModule {
    @Binds
    abstract fun bindPickupDetailsRepo(psqlPickupDetailsRepo: PsqlPickupDetailsRepo): PickupDetailsRepo
}