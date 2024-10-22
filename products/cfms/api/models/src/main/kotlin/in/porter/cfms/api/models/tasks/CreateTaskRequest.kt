package `in`.porter.cfms.api.models.tasks

import java.time.Instant

data class CreateTaskRequest(
    val flowType: String,
    val status: String,
    val packageReceived: Int?,
    val scheduledSlot: Instant?,
    val teamId: String
)
