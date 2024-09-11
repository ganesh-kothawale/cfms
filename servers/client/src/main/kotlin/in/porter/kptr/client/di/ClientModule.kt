package `in`.porter.kptr.client.di

import `in`.porter.kptr.client.clients.KptrHttpClient
import `in`.porter.kptr.client.clients.KptrClient
import dagger.Binds
import dagger.Module

@Module
internal abstract class ClientModule {

  @Binds
  abstract fun provideAsyncJobsClient(client: KptrHttpClient): KptrClient
}
