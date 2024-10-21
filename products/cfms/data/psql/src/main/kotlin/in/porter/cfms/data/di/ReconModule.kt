package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.recon.repos.PsqlReconRepo
import `in`.porter.cfms.domain.recon.repos.ReconRepo

@Module
abstract class ReconModule {

    @Binds
    abstract fun bindReconRepo(psqlReconRepo: PsqlReconRepo): ReconRepo
}
