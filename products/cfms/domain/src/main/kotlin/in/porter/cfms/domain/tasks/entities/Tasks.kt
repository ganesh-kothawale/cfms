package `in`.porter.cfms.domain.tasks.entities

import java.time.Instant

data class Tasks (
    val taskId: String,
    val flowType: String,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
