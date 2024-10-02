//import `in`.porter.cfms.api.factories.CreateOrderRequestFactory
//import `in`.porter.cfms.api.models.orders.CreateOrderApiRequest
//import `in`.porter.cfms.api.models.orders.CreateOrderResponse
//import `in`.porter.cfms.api.service.orders.mappers.CreateOrderApiRequestMapper
//import `in`.porter.cfms.api.service.orders.usecases.CreateOrderService
//import `in`.porter.cfms.servers.ktor.usecases.orders.CreateOrderHTTPService
//import io.ktor.application.*
//import io.ktor.features.ContentNegotiation
//import io.ktor.http.*
//import io.ktor.jackson.jackson
//import io.ktor.routing.*
//import io.ktor.server.testing.*
//import io.mockk.*
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//
//class CreateOrderHTTPServiceTest {
//
//    private lateinit var createOrderService: CreateOrderService
//    private lateinit var apiMapper: CreateOrderApiRequestMapper
//    private lateinit var createOrderHTTPService: CreateOrderHTTPService
//
//    @BeforeEach
//    fun setup() {
//        createOrderService = mockk()
//        apiMapper = mockk()
//        createOrderHTTPService = CreateOrderHTTPService(createOrderService, apiMapper)
//    }
//
//    @Test
//    fun `invoke should return OK when service does not throw exception`() = testApplication {
//        install(ContentNegotiation) {
//            jackson {
//                registerModule(com.fasterxml.jackson.module.kotlin.KotlinModule()) // Add Kotlin support
//                registerModule(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule()) // Add support for LocalDate and other Java 8 types
//            }
//        }
//
//        routing {
//            post("/create-order") {
//                createOrderHTTPService.invoke(call)
//            }
//        }
//
//        val requestJson = """
//            {
//                "table_type": "order",
//                "data": {
//                    "franchise_id": "franchiseId",
//                    "zorp_team_id": "teamId",
//                    "order_number": "orderNumber",
//                    "awb_number": "awbNumber",
//                    "account_id": 123,
//                    "account_code": "accountCode",
//                    "courier_partner_name": "courierPartnerName",
//                    "mode_of_transport": "modeOfTransport",
//                    "order_status": ["orderStatus"],
//                    "sender_name": "senderName",
//                    "sender_mobile_number": "senderMobileNumber",
//                    "sender_house_number": "senderHouseNumber",
//                    "sender_address_details": "senderAddressDetails",
//                    "sender_city_name": "senderCityName",
//                    "sender_state_name": "senderStateName",
//                    "sender_pincode": 123456,
//                    "sender_lat": 12.34,
//                    "sender_long": 56.78,
//                    "receiver_name": "receiverName",
//                    "receiver_mobile_number": "receiverMobileNumber",
//                    "receiver_house_number": "receiverHouseNumber",
//                    "receiver_address_details": "receiverAddressDetails",
//                    "receiver_city_name": "receiverCityName",
//                    "receiver_state_name": "receiverStateName",
//                    "shipping_label_link": "http://example.com/label",
//                    "pick_up_date": "2023-10-01",
//                    "material_type": "materialType",
//                    "material_weight": 1000,
//                    "length": 10.0,
//                    "breadth": 5.0,
//                    "height": 2.0,
//                    "volumetric_weight": 10,
//                    "receiver_pincode": 123456
//                }
//            }
//        """.trimIndent()
//
//        val mappedRequest = mockk<CreateOrderApiRequest>()
//        val serviceResponse = CreateOrderResponse(/* initialize with expected response data */)
//
//        coEvery { apiMapper.toRequestV1(any()) } returns mappedRequest
//        coEvery { createOrderService.invoke(any()) } returns serviceResponse
//
//        handleRequest(HttpMethod.Post, "/create-order") {
//            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
//            setBody(requestJson)
//        }.apply {
//            Assertions.assertEquals(HttpStatusCode.OK, response.status())
//            // Add assertions for the response body if needed
//        }
//
//        coVerify { apiMapper.toRequestV1(any()) }
//        coVerify { createOrderService.invoke(any()) }
//    }
//}