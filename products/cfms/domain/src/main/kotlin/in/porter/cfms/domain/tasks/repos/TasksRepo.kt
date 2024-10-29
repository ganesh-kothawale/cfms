package `in`.porter.cfms.domain.tasks.repos

import `in`.porter.cfms.domain.tasks.entities.DomainListTasksRequest
import `in`.porter.cfms.domain.tasks.entities.ListTasks
import `in`.porter.cfms.domain.tasks.entities.Tasks

interface TasksRepo {
    suspend fun findAllTasks(request: DomainListTasksRequest): List<ListTasks>
    suspend fun countAllTasks(request: DomainListTasksRequest): Int
    suspend fun findTasksByIds(taskIds: List<String>): List<Tasks>
    suspend fun updateStatusForTasks(taskIds: List<String>, status: String)
    suspend fun create(task: Tasks): String
    suspend fun findTaskById(taskId: String): Tasks?
    suspend fun update(task: Tasks)
    suspend fun deleteTaskById(taskId: String)
}
