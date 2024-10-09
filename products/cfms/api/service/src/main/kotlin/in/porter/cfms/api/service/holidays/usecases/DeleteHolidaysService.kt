package `in`.porter.cfms.api.service.holidays.usecases



import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.usecases.DeleteHoliday
import javax.inject.Inject

class DeleteHolidaysService @Inject constructor(
    private val deleteHolidayDomainService: DeleteHoliday
) {

    suspend fun deleteHoliday(holidayId: Int) {
        try {
            deleteHolidayDomainService.deleteHoliday(holidayId)
        } catch (e: CfmsException) {
            throw CfmsException("Unexpected error occurred while deleting holiday: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Unexpected error occurred while deleting holiday: ${e.message}")
        }
    }
}
