package `in`.porter.cfms.data.orders.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.orders.repos.PsqlOrderDetailsRepo
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo

@Module
abstract class OrderDetailsRepoModule {
    @Binds
    abstract fun provideOrderDetailsRepo(orderDetailsRepo: PsqlOrderDetailsRepo): OrderDetailsRepo

}