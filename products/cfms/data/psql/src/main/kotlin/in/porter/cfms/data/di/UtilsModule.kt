package `in`.porter.cfms.data.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
internal object UtilsModule {

  @Provides
  @JvmStatic
  fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO

}
