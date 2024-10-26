package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.holidays.repos.PsqlHolidayRepo
import `in`.porter.cfms.data.recon.repos.PsqlReconRepo
import `in`.porter.cfms.domain.holidays.repos.HolidayRepo
import `in`.porter.cfms.domain.packageIssue.repos.PackageIssueRepo

@Module
abstract class PackageIssueModule {

    @Binds
    abstract fun bindReconRepo(psqlReconRepo: PsqlReconRepo): PackageIssueRepo
}
