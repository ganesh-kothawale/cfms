package `in`.porter.cfms.domain.tasks.repos

import `in`.porter.cfms.domain.tasks.entities.ListTasks

interface TasksRepo {
    suspend fun findAllTasks(page: Int, size: Int): List<ListTasks>
    suspend fun countAllTasks(): Int
    suspend fun findTasksByIds(taskIds: List<Int>): List<ListTasks>
    suspend fun updateStatusForTasks(taskIds: List<Int>, status: String)
}