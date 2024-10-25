package `in`.porter.cfms.domain.franchise.usecases.internal

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.exceptions.FranchiseAlreadyExistsException
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.franchise.entities.RecordFranchiseDetailsRequest
import `in`.porter.cfms.domain.holidays.usecases.CreateHoliday
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class RecordFranchiseDetails
@Inject
constructor(
    private val repo: FranchiseRepo,
    private val createFranchise: CreateFranchise
) : Traceable {
    private val logger = LoggerFactory.getLogger(CreateHoliday::class.java)
    suspend fun invoke(req: RecordFranchiseDetailsRequest): String {
        repo.getByEmail(req.poc.email)?.let {
            throw FranchiseAlreadyExistsException(req.poc.email)
        }
        try {
            return createFranchise.invoke(req)
        } catch (e: CfmsException) {
            logger.error("Error creating franchise: ${e.message}")
            throw CfmsException("Failed to create franchise: ${e.message}")
        }
    }
}
