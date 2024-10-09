package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.api.service.orders.factories.CreateOrderRequestFactory
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
class CreateOrderTest {

    private lateinit var repo: OrderDetailsRepo
    private lateinit var createOrderService: CreateOrder

    @BeforeAll
    fun setup() {
        repo = mockk()
        createOrderService = CreateOrder(repo)
    }

    @BeforeEach
    fun clearMocks() {
        clearAllMocks()
    }

    @Test
    fun `should invoke repo with createOrderRequest`() = runBlocking {
        val createOrderRequest = CreateOrderRequestFactory.buildCreateOrderRequest()
        coEvery { repo.createOrder(createOrderRequest) } returns  1

        createOrderService.invoke(createOrderRequest)

        coVerify(exactly = 1) { repo.createOrder(createOrderRequest) }
    }

    @Test
    fun `should throw IllegalArgumentException for invalid request`() = runBlocking {
        val createOrderRequest = CreateOrderRequestFactory.buildCreateOrderRequest()
        coEvery { repo.createOrder(createOrderRequest) } throws IllegalArgumentException("Invalid request")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            runBlocking { createOrderService.invoke(createOrderRequest) }
        }
        assertEquals("Invalid request", exception.message)

        coVerify(exactly = 1) { repo.createOrder(createOrderRequest) }
    }

    @Test
    fun `should throw Exception for internal server error`() = runBlocking {
        val createOrderRequest = CreateOrderRequestFactory.buildCreateOrderRequest()
        coEvery { repo.createOrder(createOrderRequest) } throws Exception("Internal server error")

        val exception = assertThrows(Exception::class.java) {
            runBlocking { createOrderService.invoke(createOrderRequest) }
        }

        assertEquals("Internal server error", exception.message)

        coVerify(exactly = 1) { repo.createOrder(createOrderRequest) }
    }
}