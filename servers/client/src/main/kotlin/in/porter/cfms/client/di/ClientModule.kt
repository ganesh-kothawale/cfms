package `in`.porter.cfms.client.di

import `in`.porter.cfms.client.clients.CfmsHttpClient
import `in`.porter.cfms.client.clients.CfmsClient
import dagger.Binds
import dagger.Module

@Module
internal abstract class ClientModule {

  @Binds
  abstract fun provideAsyncJobsClient(client: CfmsHttpClient): CfmsClient
}
