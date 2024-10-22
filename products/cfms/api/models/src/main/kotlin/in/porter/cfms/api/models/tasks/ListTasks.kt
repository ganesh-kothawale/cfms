package `in`.porter.cfms.api.models.tasks

import java.time.LocalDateTime

data class ListTasks(
    val taskId: String,
    val flowType: String,
    val status: String,
    val packageReceived: Int?,
    val scheduledSlot: String?,
    val teamId: String,
    val createdAt: String,
    val updatedAt: String
)
