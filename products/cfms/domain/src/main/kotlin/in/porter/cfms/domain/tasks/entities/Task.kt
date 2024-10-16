package `in`.porter.cfms.domain.tasks.entities

import java.time.Instant
import java.time.LocalDateTime

data class Task(
    val taskId: Int,
    val flowType: String,
    val status: String,
    val packageReceived: Int,
    val scheduledSlot: Instant,
    val teamId: Int,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
