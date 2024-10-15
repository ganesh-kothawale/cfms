package `in`.porter.cfms.domain.tasks.entities

import java.time.LocalDateTime

data class ListTasks (
    val taskId: Int,
    val flowType: String,
    val status: String,
    val packageReceived: Int,
    val scheduledSlot: String,
    val teamId: Int,
    val createdAt: String,
    val updatedAt: String
)