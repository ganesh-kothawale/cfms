package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.AddressDetailsFactory
import `in`.porter.cfms.domain.orders.entities.PersonalInfo as DomainPersonalInfo
import `in`.porter.cfms.api.models.orders.PersonalInfo as ApiPersonalInfo
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonalInfoMapperTest {

    private lateinit var personalInfoMapper: PersonalInfoMapper

    @BeforeAll
    fun init() {
        personalInfoMapper = PersonalInfoMapper()
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map api personal info to domain personal info`() {
        val apiPersonalInfo: ApiPersonalInfo = AddressDetailsFactory.buildApiPersonalInfo()
        val expectedDomainPersonalInfo: DomainPersonalInfo = AddressDetailsFactory.buildDomainPersonalInfo()

        val result: DomainPersonalInfo = personalInfoMapper.map(apiPersonalInfo)

        Assertions.assertEquals(expectedDomainPersonalInfo.name, result.name)
        Assertions.assertEquals(expectedDomainPersonalInfo.mobileNumber, result.mobileNumber)
    }
}