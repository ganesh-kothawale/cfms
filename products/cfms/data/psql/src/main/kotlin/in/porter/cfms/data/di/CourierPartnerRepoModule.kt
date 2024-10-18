package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.courierPartners.repos.PsqlCourierPartnerRepo
import `in`.porter.cfms.domain.courierPartners.repos.CourierPartnersRepo

@Module
abstract class CourierPartnerRepoModule {

  @Binds
  abstract fun provideCourierPartnerRepo(repo: PsqlCourierPartnerRepo): CourierPartnersRepo
}
