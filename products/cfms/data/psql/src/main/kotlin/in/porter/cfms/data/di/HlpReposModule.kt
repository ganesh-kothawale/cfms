package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.hlp.repos.PsqlHlpsRepo
import `in`.porter.cfms.domain.hlp.repos.HlpsRepo

@Module
abstract class HlpReposModule {

    @Binds
    abstract fun provideHlpsRepo(repo: PsqlHlpsRepo): HlpsRepo
}