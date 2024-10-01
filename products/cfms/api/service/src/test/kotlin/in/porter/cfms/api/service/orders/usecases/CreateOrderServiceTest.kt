package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.orders.CreateOrderApiRequest
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequestV1
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderApiRequestMapper
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderRequestMapper
import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.usecases.CreateOrderService as DomainCreateOrderService
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateOrderServiceTest {

    private lateinit var service: DomainCreateOrderService
    private lateinit var apiMapper: CreateOrderApiRequestMapper
    private lateinit var mapper: CreateOrderRequestMapper
    private lateinit var createOrderService: CreateOrderService

    @BeforeEach
    fun setup() {
        service = mockk()
        apiMapper = mockk()
        mapper = mockk()
        createOrderService = CreateOrderService(service, apiMapper, mapper)
        clearAllMocks()
    }

    @Test
    fun `should invoke service with mapped request`() = runBlocking {
        val apiRequest = mockk<CreateOrderApiRequest>()
        val requestV1 = mockk<CreateOrderApiRequestV1>()
        val domainRequest = mockk<CreateOrderRequest>()

        coEvery { apiMapper.toRequestV1(apiRequest) } returns requestV1
        coEvery { mapper.toDomain(requestV1) } returns domainRequest
        coEvery { service.invoke(domainRequest) } just Runs

        createOrderService.invoke(apiRequest)

        coVerify { apiMapper.toRequestV1(apiRequest) }
        coVerify { mapper.toDomain(requestV1) }
        coVerify { service.invoke(domainRequest) }
    }

    @Test
    fun `should throw IllegalArgumentException for invalid request`() = runBlocking {
        val apiRequest = mockk<CreateOrderApiRequest>()

        coEvery { apiMapper.toRequestV1(apiRequest) } throws IllegalArgumentException("Invalid request")

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { createOrderService.invoke(apiRequest) }
        }

        coVerify { apiMapper.toRequestV1(apiRequest) }
        coVerify(exactly = 0) { mapper.toDomain(any()) }
        coVerify(exactly = 0) { service.invoke(any()) }
    }

    @Test
    fun `should throw Exception for internal server error`() = runBlocking {
        val apiRequest = mockk<CreateOrderApiRequest>()
        val requestV1 = mockk<CreateOrderApiRequestV1>()

        coEvery { apiMapper.toRequestV1(apiRequest) } returns requestV1
        coEvery { mapper.toDomain(requestV1) } throws Exception("Internal server error")

        assertThrows(Exception::class.java) {
            runBlocking { createOrderService.invoke(apiRequest) }
        }

        coVerify { apiMapper.toRequestV1(apiRequest) }
        coVerify { mapper.toDomain(requestV1) }
        coVerify(exactly = 0) { service.invoke(any()) }
    }
}