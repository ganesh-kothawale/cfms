package `in`.porter.cfms.domain.tasks.entities

import java.time.Instant

data class Tasks (
    val taskId: String,
    val flowType: String,
    val status: String,
    val packageReceived: Int?,
    val scheduledSlot: Instant?,
    val teamId: String,
    val createdAt: Instant?,
    val updatedAt: Instant?
)