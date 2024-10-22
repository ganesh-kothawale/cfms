package `in`.porter.cfms.api.service.holidays.usecases



import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.usecases.DeleteHoliday
import javax.inject.Inject

class DeleteHolidaysService @Inject constructor(
    private val deleteHolidayDomainService: DeleteHoliday,
    private val createAuditLogService: CreateAuditLogService
) {

    suspend fun deleteHoliday(holidayId: Int) {
        try {
            deleteHolidayDomainService.deleteHoliday(holidayId)

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
            throw CfmsException("Unexpected error occurred while deleting holiday: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Unexpected error occurred while deleting holiday: ${e.message}")
        }
    }
}
