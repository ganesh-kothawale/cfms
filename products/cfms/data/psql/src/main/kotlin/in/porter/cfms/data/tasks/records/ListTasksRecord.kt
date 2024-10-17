package `in`.porter.cfms.data.tasks.records

data class ListTasksRecord(
    val taskId: String,
    val flowType: String,
    val status: String,
    val packageReceived: Int?,
    val scheduledSlot: String?,
    val teamId: String,
    val createdAt: String,
    val updatedAt: String
)
