package `in`.porter.cfms.api.models.pickupTasks

data class PickupTasksResponse (
    val pickupTasks: List<PickupTask>,
    val page: Int,
    val size: Int,
    val totalRecords: Int,
    val totalPages: Int
)
