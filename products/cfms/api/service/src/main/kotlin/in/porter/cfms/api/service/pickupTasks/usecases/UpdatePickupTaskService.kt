package `in`.porter.cfms.api.service.pickupTasks.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.pickupTasks.UpdatePickupTaskRequest
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.pickupTasks.mappers.UpdatePickupTaskRequestMapper
import `in`.porter.cfms.domain.pickuptasks.usecases.internal.UpdatePickupTask
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdatePickupTaskService @Inject constructor(
    private val updatePickupTask: UpdatePickupTask, // Domain layer use case
    private val updatePickupTaskRequestMapper: UpdatePickupTaskRequestMapper,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(UpdatePickupTaskService::class.java)

    suspend fun invoke(request: UpdatePickupTaskRequest) {
        try {
            logger.info("Received request to update pickup task: ${request.taskId}")

            // Map the request to the domain entity
            val domainPickupTask = updatePickupTaskRequestMapper.toDomain(request)

            // 1. Create pickup image mapping
            updatePickupTask.createPickupImageMapping(domainPickupTask)

            // 2. Update task status and log the audit
            updatePickupTask.updateTaskStatus(domainPickupTask)
            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = request.taskId,
                    entityType = "Task",
                    status = domainPickupTask.taskStatus,
                    message = "Task status updated successfully",
                    updatedBy = 123 // Replace with actual user ID
                )
            )

            // 3. Update order status and log the audit for each order
            updatePickupTask.updateOrderStatus(domainPickupTask)
            domainPickupTask.orders.forEach { order ->
                createAuditLogService.createAuditLog(
                    CreateAuditLogRequest(
                        entityId = order.orderId,
                        entityType = "Order",
                        status = order.status,
                        message = "Order status updated successfully",
                        updatedBy = 123 // Replace with actual user ID
                    )
                )
            }

            logger.info("Pickup task updated successfully for ID: ${request.taskId}")

        } catch (e: CfmsException) {
            logger.error("Validation error: ${e.message}")
            throw e
        } catch (ne: NoSuchElementException) {
            logger.error("Not Found: ${ne.message}")
            throw ne
        }
    }
}
