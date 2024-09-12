package `in`.porter.cfms.servers.pubsub.di

import com.google.pubsub.v1.ProjectSubscriptionName
import dagger.Module
import dagger.Provides
import `in`.porter.cfms.servers.commons.di.ApplicationConfig
import `in`.porter.cfms.servers.pubsub.consumers.AsyncJobService
import `in`.porter.gcu.pubsub.subscriber.Builder
import `in`.porter.gcu.pubsub.subscriber.Subscriber

@Module
object PubSubModule {

  @Provides
  fun providesSubscriber(service: AsyncJobService): Subscriber {
    val subscription = ProjectSubscriptionName.of(ApplicationConfig.tasksProject, ApplicationConfig.pubsubSubscription)
    return  Builder(subscription, service).build()
  }

}
