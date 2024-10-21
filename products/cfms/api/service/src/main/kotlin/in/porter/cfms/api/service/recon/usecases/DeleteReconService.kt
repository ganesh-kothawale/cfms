package `in`.porter.cfms.api.service.recon.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.domain.recon.usecases.DeleteRecon
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeleteReconService @Inject constructor(
    private val deleteRecon: DeleteRecon,
    private val createAuditLogService: CreateAuditLogService
) {

    private val logger = LoggerFactory.getLogger(DeleteReconService::class.java)

    suspend fun deleteRecon(reconId: String) {
        try {
            logger.info("Request received to delete recon with ID: $reconId")

            // Forward the request to the domain use case
            deleteRecon.delete(reconId)

            logger.info("Recon with ID $reconId deleted successfully")

            // Create audit log after the recon is successfully deleted
            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = reconId,
                    entityType = "Recon",
                    status = "Deleted",
                    message = "Recon deleted successfully",
                    //TODO : currently passing hardcoded user ID once UserID development is done need to replace with actual user ID value
                    updatedBy = 123
                )
            )

        } catch (e: CfmsException) {
            logger.error("Recon deletion error: ${e.message}")
            throw CfmsException("Failed to delete recon.")
        } catch (e: NoSuchElementException) {
            logger.error("Recon not found: ${e.message}")
            throw CfmsException("Recon with ID $reconId not found.")
        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}", e)
            throw CfmsException("An unexpected error occurred on the server.")
        }
    }
}
