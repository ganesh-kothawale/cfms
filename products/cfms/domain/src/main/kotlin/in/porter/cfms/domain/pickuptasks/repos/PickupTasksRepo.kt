package `in`.porter.cfms.domain.pickuptasks.repos

import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask

import java.util.UUID

interface PickupTasksRepo {
    suspend fun findAllPickupTasks(page: Int, size: Int): List<PickupTask>
    suspend fun countAllPickupTasks(): Int
    suspend fun findPickupTasksByIds(taskIds: List<Int>): List<PickupTask>
    suspend fun updateStatusForPickupTasks(taskIds: List<Int>, status: String)
    suspend fun getPickupDetailsIdByTaskId(taskId: String): String?
    suspend fun updateByTaskId(taskId: String, orderImage: List<UUID>, packageReceived: Int?)
    suspend fun updateOrderStatuses(orders: List<Pair<String, String>>)
}