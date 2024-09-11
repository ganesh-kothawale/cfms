package `in`.porter.kptr.servers.sqs.consumers

import `in`.porter.kotlinutils.commons.country.entities.CountryContext
import `in`.porter.kotlinutils.commons.workerpools.stream.WorkAction
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.kotlinutils.sqs.SQSMessage
import `in`.porter.kptr.api.models.async.AsyncJob
import io.sentry.Sentry
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class AsyncEventWorkAction
@Inject
constructor(
  private val service: AsyncJobService
) : WorkAction<SQSMessage<AsyncJob>, SQSMessage<AsyncJob>>, Traceable {

  companion object : Logging

  override suspend fun invoke(input: SQSMessage<AsyncJob>): SQSMessage<AsyncJob>? = trace("sqs.consume") { span ->
    try {
      span.setTag("resource.name", input.obj.javaClass.simpleName)
      val countryContext = getCountryContext(input)
      withContext(countryContext) {
        service.invoke(input.obj)
        input
      }
    } catch (e: Exception) {
      logger.error(e) { "Exception occurred while processing event ${input.obj}" }
      Sentry.capture(e)
      null
    }
  }

  private suspend fun getCountryContext(input: SQSMessage<AsyncJob>): CountryContext = trace {
    return@trace CountryContext.fromHeaders(
      input.messageAttributes.map { it.key to it.value.stringValue() }.toMap()
    )
  }
}
