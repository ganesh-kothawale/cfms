package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateOrderServiceTest {

    private lateinit var repo: OrderDetailsRepo
    private lateinit var createOrderService: CreateOrderService

    @BeforeEach
    fun setup() {
        repo = mockk()
        createOrderService = CreateOrderService(repo)
        clearAllMocks()
    }

    @Test
    fun `should invoke repo with createOrderRequest`() = runBlocking {
        val createOrderRequest = mockk<CreateOrderRequest>()

        coEvery { repo.createOrder(createOrderRequest) } just Runs

        createOrderService.invoke(createOrderRequest)

        coVerify { repo.createOrder(createOrderRequest) }
    }

    @Test
    fun `should throw IllegalArgumentException for invalid request`() = runBlocking {
        val createOrderRequest = mockk<CreateOrderRequest>()

        coEvery { repo.createOrder(createOrderRequest) } throws IllegalArgumentException("Invalid request")

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { createOrderService.invoke(createOrderRequest) }
        }

        coVerify { repo.createOrder(createOrderRequest) }
    }

    @Test
    fun `should throw Exception for internal server error`() = runBlocking {
        val createOrderRequest = mockk<CreateOrderRequest>()

        coEvery { repo.createOrder(createOrderRequest) } throws Exception("Internal server error")

        assertThrows(Exception::class.java) {
            runBlocking { createOrderService.invoke(createOrderRequest) }
        }

        coVerify { repo.createOrder(createOrderRequest) }
    }
}