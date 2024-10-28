package `in`.porter.cfms.api.models.tasks

import java.time.LocalDateTime

data class TaskResponse(
    val taskId: String,
    val flowType: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)
