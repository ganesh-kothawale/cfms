package `in`.porter.cfms.domain.franchise.usecases.internal

import `in`.porter.cfms.domain.franchise.entities.ListFranchise
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListFranchises
@Inject
constructor(
    private val franchiseRepo: FranchiseRepo
) {

    private val logger = LoggerFactory.getLogger(ListFranchises::class.java)

    suspend fun listFranchises(page: Int, size: Int): FranchiseResult {
        logger.info("Listing franchises with page: $page, size: $size")

        // Fetch the total number of records
        val totalRecords = franchiseRepo.countAll()

        // Fetch the paginated list of franchises
        val franchises = franchiseRepo.findAll(page, size)

        // Log the result
        logger.info("Fetched ${franchises.size} franchises out of $totalRecords total records.")

        // Return the result with the fetched data and total record count
        return FranchiseResult(
            data = franchises,
            totalRecords = totalRecords
        )
    }
}

data class FranchiseResult(
    val data: List<ListFranchise>,
    val totalRecords: Int
)
