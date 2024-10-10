package `in`.porter.cfms.data.franchise.records

import java.time.Instant

data class FranchiseRecord (
    val franchiseId: String,
    val data: FranchiseRecordData,
    val createdAt: Instant,
    val updatedAt: Instant
)