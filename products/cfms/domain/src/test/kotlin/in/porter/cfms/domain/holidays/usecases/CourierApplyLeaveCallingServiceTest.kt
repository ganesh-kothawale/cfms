package `in`.porter.cfms.domain.holidays.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.holidays.usecases.CourierApplyLeaveCallingService.ApplyLeaveRequest
import io.ktor.client.*
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CourierApplyLeaveCallingServiceTest {

    private lateinit var courierService: CourierApplyLeaveCallingService
    private lateinit var mockHttpClient: HttpClient

    @BeforeEach
    fun setup() {
        mockHttpClient = mockk()
        courierService = CourierApplyLeaveCallingService(mockHttpClient)
    }

    @Test
    fun `should apply leave successfully`() = runBlocking {
        // Arrange: Mock the HTTP response
        val mockResponse = createMockHttpResponse(HttpStatusCode.OK, """{"message": "Leave applied successfully"}""")

        // Mocking HttpClient's post method using builder pattern
        coEvery {
            mockHttpClient.post(any<String>()) {
                contentType(ContentType.Application.Json)
                setBody(any<ApplyLeaveRequest>())  // Mocking the request body
            }
        } returns mockResponse

        // Act: Call the service to apply leave
        val applyLeaveResponse = courierService.applyLeave(
            ApplyLeaveRequest(
                identification_code = "ABC12",
                holidays = listOf("2024-12-20"),
                type = "Normal",
                backup_franchises = listOf("SME001"),
                status = "Approved"
            )
        )

        // Assert: Validate the response
        assertEquals("Leave applied successfully", applyLeaveResponse.message)

        // Verify that the post request was invoked once
        coVerify(exactly = 1) {
            mockHttpClient.post(any<String>())
        }
    }

    @Test
    fun `should throw exception when API call fails`() = runBlocking {
        // Mocking HttpResponse for failure
        val mockResponse = createMockHttpResponse(HttpStatusCode.BadRequest, """{"error": "API call failed"}""")

        // Mocking HttpClient's post method using builder pattern
        coEvery {
            mockHttpClient.post(any<String>()) {
                contentType(ContentType.Application.Json)
                setBody(any<ApplyLeaveRequest>())  // Mocking the request body
            }
        } returns mockResponse

        // Act & Assert
        val exception = assertThrows<CfmsException> {
            runBlocking {
                courierService.applyLeave(
                    ApplyLeaveRequest(
                        identification_code = "ABC12",
                        holidays = listOf("2024-12-20"),
                        type = "Normal",
                        backup_franchises = listOf("SME001"),
                        status = "Approved"
                    )
                )
            }
        }

        assertEquals("Failed to apply leave: API call failed", exception.message)

        // Verify the post call
        coVerify(exactly = 1) {
            mockHttpClient.post(any<String>())
        }
    }

    // Helper method to mock HttpResponse
    private fun createMockHttpResponse(status: HttpStatusCode, body: String): HttpResponse {
        val mockResponse = mockk<HttpResponse>()
        coEvery { mockResponse.status } returns status
        coEvery { mockResponse.bodyAsText() } returns body
        return mockResponse
    }
}