package `in`.porter.cfms.data.tasks.records

data class TaskRecord(
    val taskId: Int,
    val flowType: String,
    val status: String,
    val packageReceived: Int,
    val scheduledSlot: String,
    val teamId: Int,
    val createdAt: String,
    val updatedAt: String
)
