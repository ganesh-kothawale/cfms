package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.cpConnections.repos.PsqlCPConnectionRepo
import `in`.porter.cfms.domain.cpConnections.repos.CPConnectionRepo

@Module
abstract class CPConnectionRepoModule {

  @Binds
  abstract fun provideCPConnectionRepo(repo: PsqlCPConnectionRepo): CPConnectionRepo
}
