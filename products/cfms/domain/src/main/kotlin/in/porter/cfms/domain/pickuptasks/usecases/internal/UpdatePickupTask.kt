package `in`.porter.cfms.domain.pickuptasks.usecases.internal

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import `in`.porter.cfms.domain.pickuptasks.entities.UpdatePickupTask
import `in`.porter.cfms.domain.pickuptasks.repos.PickupTasksRepo
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject
import kotlin.NoSuchElementException

class UpdatePickupTask @Inject constructor(
    private val pickupTasksRepo: PickupTasksRepo,
    private val taskRepo: TasksRepo,
    private val orderDetailsRepo : OrderDetailsRepo
) {

    private val logger = LoggerFactory.getLogger(UpdatePickupTask::class.java)

    // 1. Create Pickup Image Mapping
    suspend fun createPickupImageMapping(pickupTask: UpdatePickupTask) {


        taskRepo.findTaskById(pickupTask.taskId)
            ?: throw NoSuchElementException("Task not found for task ID: ${pickupTask.taskId}")

        logger.info("Task found for task ID: ${pickupTask.taskId}")


        pickupTask.orders.forEach { order ->
            orderDetailsRepo.fetchOrderByOrderId(order.orderId)
                ?: throw NoSuchElementException("Order not found for order ID: ${order.orderId}")
            logger.info("Order found for order ID: ${order.orderId}")
        }

        logger.info("Creating pickup image mapping for task ID: ${pickupTask.taskId}")
        // Fetch pickup details ID from pickup_details table
        val pickupDetailsId = pickupTasksRepo.getPickupDetailsIdByTaskId(pickupTask.taskId)
            ?: throw NoSuchElementException("Pickup details not found for task ID: ${pickupTask.taskId}")

        // Now create the pickup image mapping
        pickupTasksRepo.createPickupImageMapping(
            taskID = pickupTask.taskId,
            pickupDetailsId = pickupDetailsId,
            orderImage = pickupTask.orderImage
        )
    }

    // 2. Update Task Status
    suspend fun updateTaskStatus(pickupTask: UpdatePickupTask) {
        logger.info("Updating task status for task ID: ${pickupTask.taskId}")
        pickupTasksRepo.updateTaskStatus(
            taskId = pickupTask.taskId,
            noOfPackagesReceived = pickupTask.noOfPackagesReceived,
            taskStatus = pickupTask.taskStatus
        )
    }

    // 3. Update Order Status
    suspend fun updateOrderStatus(pickupTask: UpdatePickupTask) {
        logger.info("Updating order statuses for orders")
        pickupTask.orders.forEach { order ->
            pickupTasksRepo.updateOrderStatuses(listOf(order.orderId to order.status))
        }
    }
}
