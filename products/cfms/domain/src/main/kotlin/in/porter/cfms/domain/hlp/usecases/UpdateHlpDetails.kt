package `in`.porter.cfms.domain.hlp.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.hlp.entities.UpdateHlpDetailsRequest
import `in`.porter.cfms.domain.hlp.repos.HlpsRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class UpdateHlpDetails
@Inject
constructor(
    private val repo: HlpsRepo,
) : Traceable {

    companion object : Logging

    suspend fun invoke(req: UpdateHlpDetailsRequest) = trace {
        val updateResult = repo.update(req)

        if (updateResult <= 0) {
            logger.error("Failed to update hlp record with hlp order id: ${req.hlpOrderId}")
            throw CfmsException("Failed to update hlp record. Please try again.")
        }
    }
}
