package `in`.porter.cfms.domain.pickuptasks.entities

data class PickupTaskResult (
    val data: List<PickupTask>,
    val totalRecords: Int
)