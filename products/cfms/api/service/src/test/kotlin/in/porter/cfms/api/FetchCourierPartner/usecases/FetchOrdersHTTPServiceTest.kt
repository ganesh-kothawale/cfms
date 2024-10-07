//package `in`.porter.cfms.api.FetchCourierPartner.usecases
//import courierpartner.usecases.FetchCpRecordsService
//import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiRequest
//import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiResponse
//import `in`.porter.cfms.api.models.courierpartner.Pagination
//import io.mockk.*
//import io.mockk.coVerify
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import `in`.porter.cfms.domain.exceptions.CfmsException
//import `in`.porter.cfms.servers.ktor.usecases.FetchCourierPartnerHttpService
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.mockito.Mockito.mock
//import software.amazon.awssdk.http.HttpStatusCode
//
//
//
//class FetchCourierPartnerHttpServiceTest {
//
//    private val service: FetchCpRecordsService = mockk()
//    private val call: ApplicationCall = mockk(relaxed = true)
//    private lateinit var fetchCourierPartnerHttpService: FetchCourierPartnerHttpService
//
//    @BeforeEach
//    fun setup() {
//       // call = mockk<ApplicationCall>()
//
//        fetchCourierPartnerHttpService = FetchCourierPartnerHttpService(service)
//    }
//
//    @Test
//    fun `invoke should return OK on successful request`() = runBlocking {
//        val validRequest = FetchCpRecordsApiRequest(page = 1, page_size = 10, franchise_id = 1001)
//        val validResponse = FetchCpRecordsApiResponse(data = listOf(), pagination = Pagination(1, 10, 0, 1))
//
//        // Mocking service behavior
//        coEvery { service.invoke(validRequest) } returns validResponse
//
//        // Act
//        val response = fetchCourierPartnerHttpService.invoke(call)
//
//        // Assert
//        assertEquals(validResponse, response)
//        coVerify { service.invoke(validRequest) }
//    }
//
//    @Test
//    fun `invoke should return BadRequest for invalid request format`() = runBlocking {
//        // Arrange
//        val cfmsException = CfmsException("Invalid request format")
//
//        // Mocking behavior for bad request
//        coEvery { call.receive<FetchCpRecordsApiRequest>() } throws cfmsException
//        coEvery { call.respond(HttpStatusCode.BAD_REQUEST, any<Map<String, String>>()) } returns Unit
//
//        // Act
//        fetchCourierPartnerHttpService.invoke(call)
//
//        // Assert
//        coVerify { call.respond(HttpStatusCode.BAD_REQUEST, mapOf("error" to "Invalid request format.", "details" to "Invalid request format")) }
//    }
//
//    @Test
//    fun `invoke should return UnprocessableEntity on service-level CfmsException`() = runBlocking {
//        // Arrange
//        val validRequest = FetchCpRecordsApiRequest(page = 1, page_size = 10, franchise_id = 1001)
//        val cfmsException = CfmsException("Error during processing")
//
//        // Mocking behavior
//        coEvery { call.receive<FetchCpRecordsApiRequest>() } returns validRequest
//        coEvery { service.invoke(validRequest) } throws cfmsException
//        coEvery { call.respond(HttpStatusCode.OK, cfmsException) } returns Unit
//
//        // Act
//        fetchCourierPartnerHttpService.invoke(call)
//
//        // Assert
//        coVerify { service.invoke(validRequest) }
//        coVerify { call.respond(HttpStatusCode.OK, cfmsException) }
//    }
//}
