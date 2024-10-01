package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.CreateOrderApiRequestFactory
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequest
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequestV1
import io.mockk.clearAllMocks
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateOrderApiRequestMapperTest {

    private lateinit var createOrderApiRequestMapper: CreateOrderApiRequestMapper

    @BeforeAll
    fun init() {
        createOrderApiRequestMapper = CreateOrderApiRequestMapper()
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map CreateOrderApiRequest to CreateOrderApiRequestV1`() {
        val apiRequest: CreateOrderApiRequest = CreateOrderApiRequestFactory.buildCreateOrderApiRequest()
        val expectedRequestV1: CreateOrderApiRequestV1 = CreateOrderApiRequestFactory.buildCreateOrderApiRequestV1()

        val result: CreateOrderApiRequestV1 = createOrderApiRequestMapper.toRequestV1(apiRequest)

        Assertions.assertEquals(expectedRequestV1, result)
    }
}