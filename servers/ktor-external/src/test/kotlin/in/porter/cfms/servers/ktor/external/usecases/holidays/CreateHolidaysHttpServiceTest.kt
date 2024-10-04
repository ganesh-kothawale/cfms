package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import `in`.porter.cfms.api.service.holidays.usecases.CreateHolidaysService
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
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

class CreateHolidaysHttpServiceTest {

    private lateinit var createHolidaysService: CreateHolidaysService
    private lateinit var createHolidaysHttpService: CreateHolidaysHttpService

    @BeforeEach
    fun setup() {
        createHolidaysService = mockk() // Mock the service
        createHolidaysHttpService = CreateHolidaysHttpService(createHolidaysService)
    }

    @Test
    fun `should return Created when service succeeds`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build()) // Updated KotlinModule usage
                registerModule(JavaTimeModule()) // JavaTime support for LocalDate
            }
        }

        routing {
            post("/holidays") {
                createHolidaysHttpService.invoke(call)
            }
        }

        // Define the request JSON
        val requestJson = """
            {
                "franchiseId": "ABC12",
                "startDate": "2024-01-01",
                "endDate": "2024-01-02",
                "holidayName": "New Year",
                "leaveType": "Normal"
            }
        """.trimIndent()

        // Mock service response
        coEvery { createHolidaysService.invoke(any()) } returns 1

        // Send the POST request and check the response
        client.post("/holidays") {
            contentType(ContentType.Application.Json)
            setBody(requestJson)
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
            assertTrue(bodyAsText().contains("holiday_id"))
        }
    }

    @Test
    fun `should return BadRequest for invalid input`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build())
                registerModule(JavaTimeModule())
            }
        }

        routing {
            post("/holidays") {
                createHolidaysHttpService.invoke(call)
            }
        }

        // Define an invalid request JSON (invalid date format)
        val invalidRequestJson = """
            {
                "franchiseId": "ABC12",
                "startDate": "invalid-date",
                "endDate": "2024-01-02",
                "holidayName": "New Year",
                "leaveType": "Normal"
            }
        """.trimIndent()

        // Simulate service throwing an exception
        coEvery { createHolidaysService.invoke(any()) } throws IllegalArgumentException("Invalid input")

        // Send the POST request and check the response for bad input
        client.post("/holidays") {
            contentType(ContentType.Application.Json)
            setBody(invalidRequestJson)
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }
}
