package `in`.porter.cfms.domain.tasks.entities

data class UpdateTaskStatus(
    val taskIds: List<Int>,
    val status: String
)
