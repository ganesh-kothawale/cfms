package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.courierPartners.repos.PsqlCourierPartnerRepo
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo

@Module
abstract class CourierPartnerRepoModule {
  @Binds
  abstract fun provideCourierPartnerRepo(courierPartnerRepo: PsqlCourierPartnerRepo): CourierPartnerRepo
}