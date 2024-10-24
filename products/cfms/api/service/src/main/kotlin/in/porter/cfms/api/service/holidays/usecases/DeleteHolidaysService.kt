package `in`.porter.cfms.api.service.holidays.usecases



import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.usecases.DeleteHoliday
import org.slf4j.LoggerFactory
import javax.inject.Inject

class DeleteHolidaysService @Inject constructor(
    private val deleteHolidayDomainService: DeleteHoliday,
    private val createAuditLogService: CreateAuditLogService
) {
    private val logger = LoggerFactory.getLogger(DeleteHolidaysService::class.java)
    suspend fun invoke(holidayId: String) {
        try {
            logger.info("Deleting holiday with ID: $holidayId")
            deleteHolidayDomainService.invoke(holidayId)
            logger.info("Holiday with ID $holidayId deleted successfully")

            // Create audit log after successful deletion
            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = holidayId.toString(),
                    entityType = "Holiday",
                    status = "Deleted",
                    message = "Holiday deleted successfully",
                    // TODO: Replace hardcoded user ID with actual user ID once it's available
                    updatedBy = 123
                )
            )
        } catch (e: CfmsException) {
            logger.error("Error deleting holiday with ID $holidayId: ${e.message}")
            throw e // Re-throw the original CfmsException to preserve its details

        } catch (e: Exception) {
            logger.error("Unexpected error while deleting holiday with ID $holidayId: ${e.message}", e)
            throw CfmsException("Unexpected error occurred while deleting holiday: ${e.message}")
        }
    }
}
