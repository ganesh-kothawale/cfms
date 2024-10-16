package `in`.porter.cfms.domain.tasks.entities

data class ListTasks (
    val taskId: String,
    val flowType: String,
    val status: String,
    val packageReceived: Int?,
    val scheduledSlot: String?,
    val teamId: String,
    val createdAt: String,
    val updatedAt: String
)