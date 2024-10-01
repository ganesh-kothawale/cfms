package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.CreateOrderApiRequestFactory
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequestV1
import `in`.porter.cfms.api.service.orders.factories.CreateOrderRequestFactory
import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateOrderRequestMapperTest {

    private lateinit var createOrderRequestMapper: CreateOrderRequestMapper
    private val basicDetailsMapper: BasicDetailsMapper = mockk()
    private val addressDetailsMapper: AddressDetailsMapper = mockk()
    private val itemDetailsMapper: ItemDetailsMapper = mockk()
    private val shippingDetailsMapper: ShippingDetailsMapper = mockk()

    @BeforeAll
    fun init() {
        createOrderRequestMapper = CreateOrderRequestMapper(
            basicDetailsMapper,
            addressDetailsMapper,
            itemDetailsMapper,
            shippingDetailsMapper
        )
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map CreateOrderApiRequestV1 to CreateOrderRequest`() {
        val apiRequestV1: CreateOrderApiRequestV1 = CreateOrderApiRequestFactory.buildCreateOrderApiRequestV1()
        val expectedDomainRequest: CreateOrderRequest = CreateOrderRequestFactory.buildCreateOrderRequest()

        every { basicDetailsMapper.map(apiRequestV1.basicDetails) } returns expectedDomainRequest.basicDetails
        every { addressDetailsMapper.map(apiRequestV1.addressDetails) } returns expectedDomainRequest.addressDetails
        every { itemDetailsMapper.map(apiRequestV1.itemDetails) } returns expectedDomainRequest.itemDetails
        every { shippingDetailsMapper.map(apiRequestV1.shippingDetails) } returns expectedDomainRequest.shippingDetails

        val result: CreateOrderRequest = createOrderRequestMapper.toDomain(apiRequestV1)

        Assertions.assertEquals(expectedDomainRequest, result)
    }
}