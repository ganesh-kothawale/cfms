package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

class CourierApplyLeaveCallingService
@Inject
constructor(
    private val client: HttpClient
) {
    private val logger = LoggerFactory.getLogger(CourierApplyLeaveCallingService::class.java)


    data class ApplyLeaveRequest(
        val identification_code: String,
        val holidays: List<String>,
        val type: String,
        val backup_franchises: List<String>,
        val status: String
    )


    data class ApplyLeaveResponse(
        val message: String
    )

    suspend fun applyLeave(request: ApplyLeaveRequest): ApplyLeaveResponse {
        try {
            logger.info("Sending request to external API: $request")

            val response: HttpResponse = client.post("https://courier-nin-stag.porter.in/franchise/apply_leave") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            // Handle HTTP response status
            return if (response.status == HttpStatusCode.OK) {
                logger.info("External API call successful")
                response.body() // Deserialize the response body to ApplyLeaveResponse
            } else {
                val errorMessage = response.bodyAsText() // Extract the error message from the response
                logger.error("API call failed with status: ${response.status}, message: $errorMessage")
                throw CfmsException("Failed to apply leave: $errorMessage")
            }
        } catch (e: Exception) {
            logger.error("Unexpected error during API call: ${e.message}", e)
            throw CfmsException("Failed to apply leave: API call failed")
        }
    }
}
