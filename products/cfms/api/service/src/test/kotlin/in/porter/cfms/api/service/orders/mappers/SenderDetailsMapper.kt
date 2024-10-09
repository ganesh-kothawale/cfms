package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.AddressDetailsFactory
import `in`.porter.cfms.domain.orders.entities.SenderDetails as DomainSenderDetails
import `in`.porter.cfms.api.models.orders.SenderDetails as ApiSenderDetails
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SenderDetailsMapperTest {

    private lateinit var senderDetailsMapper: SenderDetailsMapper
    private lateinit var locationMapper: LocationMapper
    private lateinit var addressMapper: AddressMapper
    private lateinit var personalInfoMapper: PersonalInfoMapper

    @BeforeAll
    fun init() {
        locationMapper = LocationMapper()
        personalInfoMapper = PersonalInfoMapper()
        addressMapper = AddressMapper()
        senderDetailsMapper = SenderDetailsMapper(personalInfoMapper,addressMapper, locationMapper, )
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map api sender details to domain sender details`() {
        val apiSenderDetails: ApiSenderDetails = AddressDetailsFactory.buildApiSenderDetails()
        val expectedDomainSenderDetails: DomainSenderDetails = AddressDetailsFactory.buildDomainSenderDetails()

        val result: DomainSenderDetails = senderDetailsMapper.map(apiSenderDetails)

        Assertions.assertEquals(expectedDomainSenderDetails.address, result.address)
        Assertions.assertEquals(expectedDomainSenderDetails.personalInfo, result.personalInfo)
    }
}