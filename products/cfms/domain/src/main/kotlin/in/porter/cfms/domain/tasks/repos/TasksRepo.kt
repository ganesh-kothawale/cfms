package `in`.porter.cfms.domain.tasks.repos

import `in`.porter.cfms.domain.tasks.entities.Tasks
import `in`.porter.cfms.domain.tasks.entities.Task

interface TasksRepo {
    suspend fun findAllTasks(page: Int, size: Int): List<Tasks>
    suspend fun countAllTasks(): Int
    suspend fun findTasksByIds(taskIds: List<String>): List<Tasks>
    suspend fun updateStatusForTasks(taskIds: List<String>, status: String)
    suspend fun create(task: Tasks): String
    suspend fun findTaskById(taskId: String): Tasks?
    suspend fun update(task: Tasks)
    suspend fun deleteTaskById(taskId: String)
}
