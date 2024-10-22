package `in`.porter.cfms.domain.pickuptasks

import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
interface PickupTasksRepo {
    suspend fun findAllPickupTasks(page: Int, size: Int): List<PickupTask>
    suspend fun countAllPickupTasks(): Int
    suspend fun findPickupTasksByIds(taskIds: List<Int>): List<PickupTask>
    suspend fun updateStatusForPickupTasks(taskIds: List<Int>, status: String)
}