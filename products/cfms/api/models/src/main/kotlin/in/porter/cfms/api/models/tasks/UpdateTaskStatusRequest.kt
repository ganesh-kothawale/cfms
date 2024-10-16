package `in`.porter.cfms.api.models.tasks

data class UpdateTaskStatusRequest(
    val taskIds: List<String>,
    val status: String
)
