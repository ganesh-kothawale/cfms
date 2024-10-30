package `in`.porter.cfms.domain.pickuptasks.usecases.internal

import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import `in`.porter.cfms.domain.pickuptasks.entities.PickupDetails
import `in`.porter.cfms.domain.pickuptasks.entities.PickupTask
import `in`.porter.cfms.domain.pickuptasks.entities.UpdatePickupTask
import `in`.porter.cfms.domain.pickuptasks.repos.PickupDetailsRepo
import `in`.porter.cfms.domain.pickuptasks.repos.PickupTasksRepo
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject
import kotlin.NoSuchElementException

class UpdatePickupTask @Inject constructor(
    private val pickupTasksRepo: PickupTasksRepo,
    private val taskRepo: TasksRepo,
    private val orderDetailsRepo: OrderDetailsRepo,
    private val pickupDetailsRepo: PickupDetailsRepo
) {

    private val logger = LoggerFactory.getLogger(UpdatePickupTask::class.java)

    suspend fun updatePickupDetails(pickupTask: UpdatePickupTask) {

        taskRepo.findTaskById(pickupTask.taskId)
            ?: throw NoSuchElementException("Task not found for task ID: ${pickupTask.taskId}")

        val pickupDetails: PickupDetails = pickupDetailsRepo.findTaskById(pickupTask.taskId)
            ?: throw NoSuchElementException("Task not found for task ID: ${pickupTask.taskId}")

        logger.info("Task found for task ID: ${pickupTask.taskId}")
        pickupTask.orders.map { it.orderId }.let { orderIds ->
            val existingOrders = orderDetailsRepo.fetchOrderByOrderId(orderIds)

            pickupTask.orders.forEach { order ->
                val existingOrder = existingOrders?.get(order.orderId)
                if (existingOrder == null) {
                    throw NoSuchElementException("Order not found for order ID: ${order.orderId}")
                } else {
                    logger.info("Order found for order ID: ${order.orderId}")
                }
            }
        }
        logger.info("Creating pickup image mapping for task ID: ${pickupTask.taskId}")
        val updatedOrderImages = (pickupDetails.orderImages ?: emptyList()) + pickupTask.orderImages
        val totalPackages = (pickupDetails.packageReceived ?: 0) + (pickupTask.noOfPackagesReceived ?: 0)
        pickupTasksRepo.updateByTaskId(
            taskId = pickupTask.taskId,
            orderImages = updatedOrderImages!!,
            packageReceived = totalPackages
        )
    }

    suspend fun updateOrderStatuses(pickupTask: UpdatePickupTask) {
        logger.info("Updating order statuses for orders")
        val orderStatusList = pickupTask.orders.map { it.orderId to it.status }
        pickupTasksRepo.updateOrderStatuses(orderStatusList)
    }
}
