package `in`.porter.cfms.servers.ktor.usecases.holidays


import `in`.porter.cfms.api.service.holidays.usecases.DeleteHolidaysService
import `in`.porter.cfms.domain.exceptions.CfmsException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import javax.inject.Inject

class DeleteHolidaysHttpService @Inject constructor(
    private val deleteHolidaysService: DeleteHolidaysService
) {
    suspend fun invoke(call: ApplicationCall, holidayId: Int) {
        try {
            deleteHolidaysService.deleteHoliday(holidayId)
            call.respond(HttpStatusCode.OK, mapOf("message" to "Holiday successfully deleted"))
        } catch (e: CfmsException) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Failed to delete holiday"))
        }
    }
}
