package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.AddressDetailsFactory
import `in`.porter.cfms.domain.orders.entities.Location as DomainLocation
import `in`.porter.cfms.api.models.orders.Location as ApiLocation
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationMapperTest {

    private lateinit var locationMapper: LocationMapper

    @BeforeAll
    fun init() {
        locationMapper = LocationMapper()
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map api location to domain location`() {
        val apiLocation: ApiLocation = AddressDetailsFactory.buildApiLocation()
        val expectedDomainLocation: DomainLocation = AddressDetailsFactory.buildDomainLocation()

        val result: DomainLocation = locationMapper.map(apiLocation)

        Assertions.assertEquals(expectedDomainLocation.latitude, result.latitude)
        Assertions.assertEquals(expectedDomainLocation.longitude, result.longitude)
    }
}