package `in`.porter.cfms.domain.usecases.external

import `in`.porter.cfms.domain.exceptions.FranchiseAlreadyExistsException
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.franchise.usecases.internal.CreateFranchise
import `in`.porter.cfms.domain.usecases.entities.RecordFranchiseDetailsRequest
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class RecordFranchiseDetails
@Inject
constructor(
    private val repo: FranchiseRepo,
    private val createFranchise: CreateFranchise
):Traceable {
    suspend fun invoke(req: RecordFranchiseDetailsRequest) {
        repo.getByCode(req.franchiseId)
            ?.let { throw FranchiseAlreadyExistsException(req.franchiseId) }
        createFranchise.invoke(req)
    }
}

