package `in`.porter.cfms.domain.franchise.usecases.internal

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.exceptions.FranchiseAlreadyExistsException
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.franchise.entities.RecordFranchiseDetailsRequest
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class RecordFranchiseDetails
@Inject
constructor(
    private val repo: FranchiseRepo,
    private val createFranchise: CreateFranchise
) : Traceable {

    suspend fun invoke(req: RecordFranchiseDetailsRequest) {
        // Check if the franchise already exists
        repo.getByEmail(req.poc.email)?.let {
            throw FranchiseAlreadyExistsException(req.poc.email)
        }
        try {
            createFranchise.invoke(req)
        } catch (e: CfmsException) {
            throw e // Rethrow the exception to propagate it up
        }
    }
}
