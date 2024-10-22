package `in`.porter.cfms.domain.tasks.entities

data class UpdateTaskStatus(
    val taskIds: List<String>,
    val status: String
)
