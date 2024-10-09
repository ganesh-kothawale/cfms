package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.api.service.holidays.usecases.DeleteHolidaysService
import `in`.porter.cfms.domain.exceptions.CfmsException
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

class DeleteHolidaysHttpServiceTest {

    private lateinit var deleteHolidaysService: DeleteHolidaysService
    private lateinit var deleteHolidaysHttpService: DeleteHolidaysHttpService

    @BeforeEach
    fun setup() {
        deleteHolidaysService = mockk() // Mock the service
        deleteHolidaysHttpService = DeleteHolidaysHttpService(deleteHolidaysService)
    }

    @Test
    fun `should return OK when service succeeds`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build())
                registerModule(JavaTimeModule())
            }
        }

        routing {
            delete("/holidays/{holidayId}") {
                val holidayId = call.parameters["holidayId"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid holiday ID")
                deleteHolidaysHttpService.invoke(call, holidayId)
            }
        }

        coEvery { deleteHolidaysService.deleteHoliday(any()) } returns Unit

        client.delete("/holidays/1") {
            accept(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Holiday successfully deleted"))
        }
    }

    @Test
    fun `should return BadRequest when CfmsException is thrown`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build())
                registerModule(JavaTimeModule())
            }
        }

        routing {
            delete("/holidays/{holidayId}") {
                val holidayId = call.parameters["holidayId"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid holiday ID")
                deleteHolidaysHttpService.invoke(call, holidayId)
            }
        }

        coEvery { deleteHolidaysService.deleteHoliday(any()) } throws CfmsException("Holiday not found")

        client.delete("/holidays/1") {
            accept(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
            assertTrue(bodyAsText().contains("Holiday not found"))
        }
    }

    @Test
    fun `should return InternalServerError when an exception is thrown`() = testApplication {
        install(ContentNegotiation) {
            jackson {
                registerModule(KotlinModule.Builder().build())
                registerModule(JavaTimeModule())
            }
        }

        routing {
            delete("/holidays/{holidayId}") {
                val holidayId = call.parameters["holidayId"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid holiday ID")
                deleteHolidaysHttpService.invoke(call, holidayId)
            }
        }

        coEvery { deleteHolidaysService.deleteHoliday(any()) } throws Exception("Unexpected error")

        client.delete("/holidays/1") {
            accept(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
            assertTrue(bodyAsText().contains("Failed to delete holiday"))
        }
    }
}
