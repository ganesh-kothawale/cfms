package `in`.porter.cfms.domain.pickuptasks.repos

import `in`.porter.cfms.domain.orders.entities.Order
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask

interface PickupTasksRepo {
    suspend fun findAllPickupTasks(page: Int, size: Int): List<PickupTask>
    suspend fun countAllPickupTasks(): Int
    suspend fun findPickupTasksByIds(taskIds: List<Int>): List<PickupTask>
    suspend fun updateStatusForPickupTasks(taskIds: List<Int>, status: String)
    suspend fun getPickupDetailsIdByTaskId(taskId: String): String?
    suspend fun createPickupImageMapping(taskID: String, pickupDetailsId: String, orderImage: String)
    suspend fun updateTaskStatus(taskId: String, noOfPackagesReceived: Int?, taskStatus: String)
    suspend fun updateOrderStatuses(orders: List<Pair<String, String>>)
}