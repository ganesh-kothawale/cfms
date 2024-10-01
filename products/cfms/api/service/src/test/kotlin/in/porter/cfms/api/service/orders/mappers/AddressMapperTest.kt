package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.AddressDetailsFactory
import `in`.porter.cfms.domain.orders.entities.Address as DomainAddress
import `in`.porter.cfms.api.models.orders.Address as ApiAddress
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressMapperTest {

    private lateinit var addressMapper: AddressMapper

    @BeforeAll
    fun init() {
        addressMapper = AddressMapper()
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map api address to domain address`() {
        val apiAddress: ApiAddress = AddressDetailsFactory.buildApiAddress()
        val expectedDomainAddress: DomainAddress = AddressDetailsFactory.buildDomainAddress()

        val result: DomainAddress = addressMapper.map(apiAddress)

        Assertions.assertEquals(expectedDomainAddress.houseNumber, result.houseNumber)
        Assertions.assertEquals(expectedDomainAddress.addressDetails, result.addressDetails)
        Assertions.assertEquals(expectedDomainAddress.cityName, result.cityName)
        Assertions.assertEquals(expectedDomainAddress.stateName, result.stateName)
        Assertions.assertEquals(expectedDomainAddress.pincode, result.pincode)
    }
}