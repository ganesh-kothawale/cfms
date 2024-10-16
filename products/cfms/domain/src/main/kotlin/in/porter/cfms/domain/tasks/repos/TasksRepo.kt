package `in`.porter.cfms.domain.tasks.repos

import `in`.porter.cfms.domain.tasks.entities.ListTasks
import `in`.porter.cfms.domain.tasks.entities.Task

interface TasksRepo {
    suspend fun findAllTasks(page: Int, size: Int): List<ListTasks>
    suspend fun countAllTasks(): Int
    suspend fun findTasksByIds(taskIds: List<String>): List<ListTasks>
    suspend fun updateStatusForTasks(taskIds: List<String>, status: String)
    suspend fun create(task: Task): String
}