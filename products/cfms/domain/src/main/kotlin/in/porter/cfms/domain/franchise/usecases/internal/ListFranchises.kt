package `in`.porter.cfms.domain.franchise.usecases.internal

import `in`.porter.cfms.domain.franchise.entities.FranchiseResult
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.franchise.entities.DomainListFranchisesRequest
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListFranchises
@Inject
constructor(
    private val franchiseRepo: FranchiseRepo
) {

    private val logger = LoggerFactory.getLogger(ListFranchises::class.java)

    suspend fun invoke(request: DomainListFranchisesRequest): FranchiseResult {
        logger.info("Listing franchises with request: $request")

        // Fetch the total number of records
        val totalRecords = franchiseRepo.countAll(request)

        // Fetch the paginated list of franchises
        val franchises = franchiseRepo.findAll(request)

        // Log the result
        logger.info("Fetched ${franchises.size} franchises out of $totalRecords total records.")

        // Return the result with the fetched data and total record count
        return FranchiseResult(
            data = franchises,
            totalRecords = totalRecords
        )
    }
}

