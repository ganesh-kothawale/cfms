package `in`.porter.cfms.api.service.pickupTasks.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.tasks.usecases.DeleteTaskService
import `in`.porter.cfms.domain.pickuptasks.usecases.internal.DeletePickupDetails
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeletePickupDetailsService
@Inject constructor(
    private val deletePickupDetails: DeletePickupDetails,
    private val createAuditLogService: CreateAuditLogService
) : Traceable {
    private val logger = LoggerFactory.getLogger(DeleteTaskService::class.java)

    suspend fun invoke(taskId: String) {
        try {
            logger.info("Request received to delete task with ID: $taskId")

            deletePickupDetails.invoke(taskId)

            logger.info("Pickup Details with ID $taskId deleted successfully")

            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = taskId,
                    entityType = "Pickup Details",
                    status = "Deleted",
                    message = "Pickup Details deleted successfully",
                    //TODO : currently passing hardcoded user ID once UserID development is done need to replace with actual user ID value
                    updatedBy = 123
                )
            )

        } catch (e: CfmsException) {
            logger.error("Pickup Details deletion error: ${e.message}")
            throw CfmsException("Failed to delete task.")
        } catch (e: NoSuchElementException) {
            logger.error("Pickup Details not found: ${e.message}")
            throw CfmsException("Pickup Details with ID $taskId not found.")
        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}", e)
            throw CfmsException("An unexpected error occurred on the server.")
        }
    }
}