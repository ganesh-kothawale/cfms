package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import `in`.porter.cfms.data.franchise.FranchiseQueries
import `in`.porter.cfms.data.franchise.mappers.FranchiseRecordMapper
import `in`.porter.cfms.data.repos.PsqlFranchisesRepo
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo

@Module
 abstract class FranchiseReposModule {
    @Binds
    abstract fun provideFranchisesRepo(repo: PsqlFranchisesRepo): FranchiseRepo
}