package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.AddressDetailsFactory
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressDetailsMapperTest {

    private lateinit var addressDetailsMapperMock: AddressDetailsMapper
    private lateinit var senderDetailsMapper: SenderDetailsMapper
    private lateinit var receiverDetailsMapper: ReceiverDetailsMapper

    @BeforeAll
    fun init(){
        senderDetailsMapper = mockk()
        receiverDetailsMapper = mockk()
        addressDetailsMapperMock = AddressDetailsMapper(senderDetailsMapper, receiverDetailsMapper)
    }

    @BeforeEach
    fun setup(){
        clearAllMocks()
    }

    @Test
    fun `invoke mapper test`() {
        val apiSenderDetails = AddressDetailsFactory.buildApiSenderDetails()
        val apiReceiverDetails = AddressDetailsFactory.buildApiReceiverDetails()
        val apiAddressDetails = AddressDetailsFactory.buildApiAddressDetails(apiSenderDetails, apiReceiverDetails)

        val domainSenderDetails = AddressDetailsFactory.buildDomainSenderDetails()
        val domainReceiverDetails = AddressDetailsFactory.buildDomainReceiverDetails()
        val domainAddressDetails = AddressDetailsFactory.buildDomainAddressDetails(domainSenderDetails, domainReceiverDetails)

        every { senderDetailsMapper.map(apiSenderDetails) } returns domainSenderDetails
        every { receiverDetailsMapper.map(apiReceiverDetails) } returns domainReceiverDetails

        val result = addressDetailsMapperMock.map(apiAddressDetails)
        Assertions.assertEquals(result.receiverDetails.address, domainAddressDetails.receiverDetails.address)
    }
}