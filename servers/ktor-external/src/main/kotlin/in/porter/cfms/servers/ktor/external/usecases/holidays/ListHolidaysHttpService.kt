package `in`.porter.cfms.servers.ktor.external.usecases.holidays

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.holidays.FranchiseAddress
import `in`.porter.cfms.api.models.holidays.FranchisePoc
import `in`.porter.cfms.api.models.holidays.FranchiseResponse
import `in`.porter.cfms.api.models.holidays.HolidayDetails
import `in`.porter.cfms.api.models.holidays.HolidayPeriod
import `in`.porter.cfms.api.models.holidays.HolidayResponse
import `in`.porter.cfms.api.models.holidays.ListHolidaysResponse
import `in`.porter.cfms.api.service.holidays.mappers.ListHolidaysRequestMapper
import `in`.porter.cfms.api.service.holidays.usecases.ListHolidaysService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

class ListHolidaysHttpService @Inject
constructor(
    private val listHolidaysService: ListHolidaysService,
    private val listHolidaysRequestMapper: ListHolidaysRequestMapper
) : Traceable {
    private val logger = LoggerFactory.getLogger(ListHolidaysHttpService::class.java)

    suspend fun invoke(
        call: ApplicationCall,
        page: Int,
        size: Int,
        franchiseId: String?,
        leaveType: String?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ) {
        try {
            logger.info("Received request to list all holidays")

            // Use the mapper to create the request model
            val request = listHolidaysRequestMapper.toDomain(
                page = page,
                size = size,
                franchiseId = franchiseId,
                leaveType = leaveType,
                startDate = startDate,
                endDate = endDate
            )
            logger.info("Received request to list all holidays with request: {}", request)

            // Call the service to list holidays
            val holidaysResponse = listHolidaysService.listHolidays(request)

            // Map to the new response structure
            val formattedResponse = ListHolidaysResponse(
                page = holidaysResponse.page,
                size = holidaysResponse.size,
                totalPages = holidaysResponse.totalPages,
                totalRecords = holidaysResponse.totalRecords,
                holidays = holidaysResponse.holidays.map { holiday ->
                    HolidayResponse(
                        holidayId = holiday.holidayId,
                        franchiseId = holiday.franchiseId,
                        holidayPeriod = HolidayPeriod(
                            fromDate = holiday.holidayPeriod.fromDate,
                            toDate = holiday.holidayPeriod.toDate
                        ),
                        holidayDetails = HolidayDetails(
                            name = holiday.holidayDetails.name,
                            leaveType = holiday.holidayDetails.leaveType.toString(),
                            backupFranchise = holiday.holidayDetails.backupFranchise
                        ),
                        franchise = FranchiseResponse(
                            franchiseId = holiday.franchise.franchiseId,
                            franchiseName = holiday.franchise.franchiseName,
                            poc = FranchisePoc(
                                name = holiday.franchise.poc.name,
                                contact = holiday.franchise.poc.contact
                            ),
                            address = FranchiseAddress(
                                gpsAddress = holiday.franchise.address.gpsAddress,
                                city = holiday.franchise.address.city,  // Replace with actual data if available
                                state = holiday.franchise.address.state // Replace with actual data if available
                            )
                        )
                    )
                }
            )

            // Respond with the formatted result
            call.respond(HttpStatusCode.OK, mapOf("data" to formattedResponse))
            logger.info("Sent response to list all holidays")
        } catch (e: CfmsException) {
            // Handle any validation or service-related exceptions
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to listOf(mapOf("message" to e.message))))
        } catch (e: Exception) {
            // Handle any unexpected errors
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to listOf(mapOf("message" to "Failed to retrieve holidays", "details" to e.message))))
        }
    }
}
