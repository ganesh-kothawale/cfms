package `in`.porter.cfms.domain.tasks.repos

import `in`.porter.cfms.domain.tasks.entities.ListTasks

interface TasksRepo {
    suspend fun findAllTasks(page: Int, size: Int): List<ListTasks>
    suspend fun countAllTasks(): Int
}