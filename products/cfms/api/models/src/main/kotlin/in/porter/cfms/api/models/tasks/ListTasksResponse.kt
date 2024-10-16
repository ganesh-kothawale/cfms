package `in`.porter.cfms.api.models.tasks

data class ListTasksResponse(
    val tasks: List<TaskResponse>,
    val page: Int,
    val limit: Int,
    val totalRecords: Int,
    val totalPages: Int
)
