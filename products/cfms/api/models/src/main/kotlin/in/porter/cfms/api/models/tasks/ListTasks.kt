package `in`.porter.cfms.api.models.tasks

import java.time.LocalDateTime

data class ListTasks(
    val taskId: Int,
    val flowType: String,
    val status: String,
    val packageReceived: Int,
    val scheduledSlot: String,
    val teamId: Int,
    val createdAt: String,
    val updatedAt: String
)
