package `in`.porter.cfms.data.tasks.records

import java.time.Instant

data class TaskRecord(
    val taskId: String,
    val flowType: String,
    val status: String,
    val packageReceived: Int?,
    val scheduledSlot: Instant?,
    val teamId: String,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
