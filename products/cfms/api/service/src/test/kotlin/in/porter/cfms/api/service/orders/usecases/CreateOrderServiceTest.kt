package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.orders.CreateOrderApiRequest
import `in`.porter.cfms.api.models.orders.CourierOrderResponse
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequestV2
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderApiRequestMapper
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderRequestMapper
import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.usecases.CreateOrder
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateOrderServiceTest {

    private lateinit var service: CreateOrder
    private lateinit var apiMapper: CreateOrderApiRequestMapper
    private lateinit var mapper: CreateOrderRequestMapper
    private lateinit var createOrderService: CreateOrderService

    @BeforeAll
    fun setup() {
        service = mockk()
        apiMapper = mockk()
        mapper = mockk()
        createOrderService = CreateOrderService(service, mapper)
    }

    @BeforeEach
    fun clearMocks() {
        clearAllMocks()
    }

    @Test
    fun `should invoke service with mapped request`() = runBlocking {
        val apiRequest = mockk<CreateOrderApiRequest>()
        val requestV2 = mockk<CreateOrderApiRequestV2>()
        val domainRequest = mockk<CreateOrderRequest>()
        val response = mockk<CourierOrderResponse>()

        coEvery { apiMapper.toRequestV1(apiRequest) } returns requestV2
        coEvery { mapper.toDomain(requestV2) } returns domainRequest
        coEvery { service.invoke(domainRequest) } returns 1
        coEvery { mapper.getResponseById(1) } returns response

        val result = createOrderService.invoke(requestV2)

        Assertions.assertEquals(response, result)
        coVerify { mapper.toDomain(requestV2) }
        coVerify { service.invoke(domainRequest) }
        coVerify { mapper.getResponseById(1) }
    }

    @Test
    fun `should throw IllegalArgumentException for invalid request`() = runBlocking {
        val requestV2 = mockk<CreateOrderApiRequestV2>()

        coEvery { mapper.toDomain(requestV2) } throws IllegalArgumentException("Invalid request")

        assertThrows<IllegalArgumentException> {
            runBlocking { createOrderService.invoke(requestV2) }
        }

        coVerify { mapper.toDomain(requestV2) }
        coVerify(exactly = 0) { service.invoke(any()) }
        coVerify(exactly = 0) { mapper.getResponseById(any()) }
    }

    @Test
    fun `should throw CourierExceptionApi when CourierException is thrown`() = runBlocking {
        val requestV2 = mockk<CreateOrderApiRequestV2>()
        val exceptionMessage = "Internal server error"

        coEvery { mapper.toDomain(requestV2) } throws Exception(
            exceptionMessage
        )

        val exception = assertThrows<Exception> {
            runBlocking {
                createOrderService.invoke(requestV2)
            }
        }

        Assertions.assertEquals(exceptionMessage, exception.message)

        coVerify { mapper.toDomain(requestV2) }
        coVerify(exactly = 0) { service.invoke(any()) }
        coVerify(exactly = 0) { mapper.getResponseById(any()) }
    }
}