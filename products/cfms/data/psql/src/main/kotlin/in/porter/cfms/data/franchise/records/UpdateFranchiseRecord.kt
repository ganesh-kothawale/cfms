package `in`.porter.cfms.data.franchise.records

import java.time.Instant

data class UpdateFranchiseRecord(
    val franchiseId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val data: UpdateFranchiseDataRecord
)
