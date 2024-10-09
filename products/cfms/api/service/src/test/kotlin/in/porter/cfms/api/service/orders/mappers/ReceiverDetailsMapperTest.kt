package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.AddressDetailsFactory
import `in`.porter.cfms.domain.orders.entities.ReceiverDetails as DomainReceiverDetails
import `in`.porter.cfms.api.models.orders.ReceiverDetails as ApiReceiverDetails
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReceiverDetailsMapperTest {

    private lateinit var receiverDetailsMapper: ReceiverDetailsMapper
    private lateinit var addressMapper: AddressMapper
    private lateinit var personalInfoMapper: PersonalInfoMapper

    @BeforeAll
    fun init() {
        addressMapper = AddressMapper()
        personalInfoMapper = PersonalInfoMapper()
        receiverDetailsMapper = ReceiverDetailsMapper(personalInfoMapper, addressMapper)
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map api receiver details to domain receiver details`() {
        val apiReceiverDetails: ApiReceiverDetails = AddressDetailsFactory.buildApiReceiverDetails()
        val expectedDomainReceiverDetails: DomainReceiverDetails = AddressDetailsFactory.buildDomainReceiverDetails()
        val result: DomainReceiverDetails = receiverDetailsMapper.map(apiReceiverDetails)

        Assertions.assertEquals(expectedDomainReceiverDetails.address, result.address)
        Assertions.assertEquals(expectedDomainReceiverDetails.personalInfo, result.personalInfo)
    }
}