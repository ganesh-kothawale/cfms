package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.HolidayDetails
import `in`.porter.cfms.api.models.holidays.HolidayPeriod
import `in`.porter.cfms.api.models.holidays.HolidayResponse
import `in`.porter.cfms.api.models.holidays.ListHolidaysResponse
import `in`.porter.cfms.api.models.holidays.FranchiseAddress
import `in`.porter.cfms.api.models.holidays.FranchisePoc
import `in`.porter.cfms.api.models.holidays.FranchiseResponse
import `in`.porter.cfms.api.models.holidays.ListHolidaysRequest
import `in`.porter.cfms.api.service.holidays.usecases.ListHolidaysService
import `in`.porter.cfms.api.service.holidays.mappers.ListHolidaysRequestMapper
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ListHolidaysHttpServiceTest {

    private lateinit var listHolidaysService: ListHolidaysService
    private lateinit var listHolidaysRequestMapper: ListHolidaysRequestMapper
    private lateinit var listHolidaysHttpService: ListHolidaysHttpService

    @BeforeEach
    fun setup() {
        listHolidaysService = mockk() // Mock the service
        listHolidaysRequestMapper = mockk() // Mock the mapper
        listHolidaysHttpService = ListHolidaysHttpService(listHolidaysService, listHolidaysRequestMapper)
    }

    @Test
    fun `should return OK with list of holidays on success`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build()) // Updated KotlinModule usage
                registerModule(JavaTimeModule()) // JavaTime support for LocalDate
            }
        }

        routing {
            get("/holidays") {
                listHolidaysHttpService.invoke(
                    call,
                    page = 1,
                    size = 10,
                    franchiseId = "franchise-123",
                    leaveType = "Normal",
                    startDate = LocalDate.of(2024, 1, 1),
                    endDate = LocalDate.of(2024, 12, 31)
                )
            }
        }

        // Mock the request and service response
        // Create an instance of ListHolidaysRequest
        val request = ListHolidaysRequest(
            page = 1,
            size = 10,
            franchiseId = "franchise-123",
            leaveType = "Normal",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        val mockResponse = ListHolidaysResponse(
            page = 1,
            size = 10,
            totalPages = 1,
            totalRecords = 1,
            holidays = listOf(
                HolidayResponse(
                    holidayId = 1,
                    franchiseId = "franchise-123",
                    holidayPeriod = HolidayPeriod(
                        fromDate = LocalDate.of(2024, 1, 1),
                        toDate = LocalDate.of(2024, 1, 10)
                    ),
                    holidayDetails = HolidayDetails(
                        name = "New Year Holiday",
                        leaveType = "Normal",
                        backupFranchise = "backup-123"
                    ),
                    franchise = FranchiseResponse(
                        franchiseId = "franchise-123",
                        franchiseName = "Franchise A",
                        poc = FranchisePoc(
                            name = "John Doe",
                            contact = "1234567890"
                        ),
                        address = FranchiseAddress(
                            gpsAddress = "123 Main St",
                            city = "Sample City",
                            state = "Sample State"
                        )
                    )
                )
            )
        )

        coEvery { listHolidaysRequestMapper.toDomain(any(), any(), any(), any(), any(), any()) } returns request
        coEvery { listHolidaysService.listHolidays(request) } returns mockResponse

        // Send the GET request and verify the response
        client.get("/holidays").apply {
            assertEquals(HttpStatusCode.OK, status)
            // Add more assertions if needed to verify the response content
        }
    }

    @Test
    fun `should return BadRequest for invalid parameters`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build()) // Updated KotlinModule usage
                registerModule(JavaTimeModule()) // JavaTime support for LocalDate
            }
        }

        routing {
            get("/holidays") {
                listHolidaysHttpService.invoke(
                    call,
                    page = 1,
                    size = 10,
                    franchiseId = null,
                    leaveType = "InvalidType",
                    startDate = LocalDate.of(2024, 1, 1),
                    endDate = LocalDate.of(2023, 12, 31) // End date before start date to trigger validation error
                )
            }
        }

        // Simulate service throwing a validation exception
        coEvery { listHolidaysRequestMapper.toDomain(any(), any(), any(), any(), any(), any()) } throws CfmsException("Invalid date range")

        // Send the GET request and verify the response
        client.get("/holidays").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
            // Add more assertions if needed to verify the response content
        }
    }

    @Test
    fun `should return InternalServerError on unexpected error`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build()) // Updated KotlinModule usage
                registerModule(JavaTimeModule()) // JavaTime support for LocalDate
            }
        }

        routing {
            get("/holidays") {
                listHolidaysHttpService.invoke(
                    call,
                    page = 1,
                    size = 10,
                    franchiseId = "franchise-123",
                    leaveType = "Normal",
                    startDate = LocalDate.of(2024, 1, 1),
                    endDate = LocalDate.of(2024, 12, 31)
                )
            }
        }

        // Simulate service throwing an unexpected exception
        coEvery { listHolidaysRequestMapper.toDomain(any(), any(), any(), any(), any(), any()) } throws RuntimeException("Unexpected error")

        // Send the GET request and verify the response
        client.get("/holidays").apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
            // Add more assertions if needed to verify the response content
        }
    }
}
