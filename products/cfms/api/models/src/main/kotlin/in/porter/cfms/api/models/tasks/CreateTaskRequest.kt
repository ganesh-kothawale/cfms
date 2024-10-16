package `in`.porter.cfms.api.models.tasks

import java.time.Instant

data class CreateTaskRequest(
    val flowType: String,
    val status: String,
    val packageReceived: Int,
    val scheduledSlot: Instant,  // Using String for now, will be converted in mapper/service
    val teamId: Int
)
