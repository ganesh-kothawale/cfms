package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import `in`.porter.cfms.api.service.holidays.usecases.UpdateHolidaysService
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateHolidaysHttpServiceTest {

    private lateinit var updateHolidaysService: UpdateHolidaysService
    private lateinit var updateHolidaysHttpService: UpdateHolidaysHttpService

    @BeforeEach
    fun setup() {
        updateHolidaysService = mockk() // Mock the service
        updateHolidaysHttpService = UpdateHolidaysHttpService(updateHolidaysService)
    }

    @Test
    fun `should return OK on successful update`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build()) // Updated KotlinModule usage
                registerModule(JavaTimeModule()) // JavaTime support for LocalDate
            }
        }

        routing {
            put("/holidays") {
                updateHolidaysHttpService.invoke(call)
            }
        }

        // Define the request JSON
        val requestJson = """
            {
                "holidayId": "123",
                "franchiseId": "ABC12",
                "startDate": "2024-01-01",
                "endDate": "2024-01-02",
                "holidayName": "Updated Holiday",
                "leaveType": "Normal",
                "backupFranchiseIds": "SME001"
            }
        """.trimIndent()

        // Mock service response
        coEvery { updateHolidaysService.invoke(any()) } returns 1

        // Send the PUT request and check the response
        client.put("/holidays") {
            contentType(ContentType.Application.Json)
            setBody(requestJson)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun `should return BadRequest for invalid input`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build()) // Updated KotlinModule usage
                registerModule(JavaTimeModule()) // JavaTime support for LocalDate
            }
        }

        routing {
            put("/holidays") {
                updateHolidaysHttpService.invoke(call)
            }
        }

        // Define an invalid request JSON (invalid date format)
        val invalidRequestJson = """
            {
                "holidayId": 123,
                "franchiseId": "test-franchise",
                "startDate": "invalid-date",
                "endDate": "2024-01-02",
                "holidayName": "Updated Holiday",
                "leaveType": "Normal",
                "backupFranchiseIds": "SME001"
            }
        """.trimIndent()

        // Simulate service throwing an exception
        coEvery { updateHolidaysService.invoke(any()) } throws IllegalArgumentException("Invalid input")

        // Send the PUT request and check the response for bad input
        client.put("/holidays") {
            contentType(ContentType.Application.Json)
            setBody(invalidRequestJson)
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }
}
