package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.franchise.repos.PsqlFranchisesRepo
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo

@Module
 abstract class FranchiseReposModule {
    @Binds
    abstract fun provideFranchisesRepo(repo: PsqlFranchisesRepo): FranchiseRepo
}