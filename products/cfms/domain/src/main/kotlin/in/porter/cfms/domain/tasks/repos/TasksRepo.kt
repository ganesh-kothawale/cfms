package `in`.porter.cfms.domain.tasks.repos

import `in`.porter.cfms.domain.tasks.entities.Tasks

interface TasksRepo {
    suspend fun findAllTasks(page: Int, size: Int): List<Tasks>
    suspend fun countAllTasks(): Int
}