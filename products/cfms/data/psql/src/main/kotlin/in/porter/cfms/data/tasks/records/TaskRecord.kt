package `in`.porter.cfms.data.tasks.records

import java.time.Instant

data class TaskRecord(
    val taskId: Int,
    val flowType: String,
    val status: String,
    val packageReceived: Int,
    val scheduledSlot: Instant,
    val teamId: Int,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
