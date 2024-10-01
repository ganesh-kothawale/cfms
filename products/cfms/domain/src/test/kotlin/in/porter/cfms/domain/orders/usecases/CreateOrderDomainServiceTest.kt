package `in`.porter.cfms.domain.orders.usecases

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.repos.OrderDetailsRepo
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateOrderServiceTest {

    private lateinit var repo: OrderDetailsRepo
    private lateinit var createOrderRequest: CreateOrderRequest
    private lateinit var createOrderService: CreateOrderService

    //TODO: Move setup to before all
    @BeforeEach
    fun setup() {
        repo = mockk()
        createOrderRequest = mockk<CreateOrderRequest>()
        createOrderService = CreateOrderService(repo)
        clearAllMocks()
    }

    @Test
    fun `should invoke repo with createOrderRequest`() = runBlocking {
        coEvery { repo.createOrder(createOrderRequest) } just Runs

        createOrderService.invoke(createOrderRequest)
        //TODO: Add exactly once clause
        coVerify { repo.createOrder(createOrderRequest) }
    }

    @Test
    fun `should throw IllegalArgumentException for invalid request`() = runBlocking {
        coEvery { repo.createOrder(createOrderRequest) } throws IllegalArgumentException("Invalid request")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            runBlocking { createOrderService.invoke(createOrderRequest) }
        }
        assertEquals("Invalid request", exception.message)

        coVerify { repo.createOrder(createOrderRequest) }
    }

    @Test
    fun `should throw Exception for internal server error`() = runBlocking {
        coEvery { repo.createOrder(createOrderRequest) } throws Exception("Internal server error")

        val exception = assertThrows(Exception::class.java) {
            runBlocking { createOrderService.invoke(createOrderRequest) }
        }

        assertEquals("Internal server error", exception.message)

        coVerify { repo.createOrder(createOrderRequest) }
    }
}