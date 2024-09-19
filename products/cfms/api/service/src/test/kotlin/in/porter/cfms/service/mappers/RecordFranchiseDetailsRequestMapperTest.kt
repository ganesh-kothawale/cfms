package `in`.porter.cfms.service.mappers

import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsRequest
import `in`.porter.cfms.api.service.franchises.mappers.RecordFranchiseDetailsRequestMapper
import `in`.porter.cfms.service.factories.RecordFranchiseDetailsRequestFactory
import `in`.porter.cfms.api.models.FranchiseStatus as ModelFranchiseStatus
import `in`.porter.cfms.domain.franchise.FranchiseStatus as DomainFranchiseStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

class RecordFranchiseDetailsRequestMapperTest {

    private lateinit var mapper: RecordFranchiseDetailsRequestMapper
    private lateinit var requestFactory: RecordFranchiseDetailsRequestFactory

    @BeforeEach
    fun setUp() {
        mapper = RecordFranchiseDetailsRequestMapper()
        requestFactory = RecordFranchiseDetailsRequestFactory()
    }

    @Test
    fun `should map request from API model to domain model correctly`() {
        // Arrange
        val request: RecordFranchiseDetailsRequest = requestFactory.createDefaultRequest()
        val franchiseId = "FR123"

        // Act
        val result = mapper.toDomain(request, franchiseId)

        // Assert
        assertEquals(franchiseId, result.franchiseId)
        assertEquals(request.poc.name, result.poc.name)
        assertEquals(request.poc.primaryNumber, result.poc.primaryNumber)
        assertEquals(request.poc.email, result.poc.email)
        assertEquals(request.address.address, result.address.address)
        assertEquals(request.address.city, result.address.city)
        assertEquals(request.address.state, result.address.state)
        assertEquals(request.address.pincode, result.address.pincode)
        assertEquals(request.radiusCoverage, result.radiusCoverage)
        assertEquals(request.hlpEnabled, result.hlpEnabled)
        assertEquals(DomainFranchiseStatus.Active, result.status)
        assertEquals(request.daysOfOperation, result.daysOfOperation)
        assertEquals(request.porterHubName, result.porterHubName)
        assertEquals(request.franchiseGst, result.franchiseGst)
        assertEquals(request.franchisePan, result.franchisePan)
        assertEquals(request.franchiseCanceledCheque, result.franchiseCanceledCheque)
        assertEquals(request.kamUser, result.kamUser)
        assertEquals(request.showCrNumber, result.showCrNumber)
    }

    @Test
    fun `should map model status to domain status correctly`() {
        // Arrange & Act
        val resultActive = mapper.mapModelToDomainStatus(ModelFranchiseStatus.Active)
        val resultInactive = mapper.mapModelToDomainStatus(ModelFranchiseStatus.Inactive)
        val resultSuspended = mapper.mapModelToDomainStatus(ModelFranchiseStatus.Suspended)

        // Assert
        assertEquals(DomainFranchiseStatus.Active, resultActive)
        assertEquals(DomainFranchiseStatus.Inactive, resultInactive)
        assertEquals(DomainFranchiseStatus.Suspended, resultSuspended)
    }

    @Test
    fun `should map domain status to model status correctly`() {
        // Arrange & Act
        val resultActive = mapper.mapDomainToModelStatus(DomainFranchiseStatus.Active)
        val resultInactive = mapper.mapDomainToModelStatus(DomainFranchiseStatus.Inactive)
        val resultSuspended = mapper.mapDomainToModelStatus(DomainFranchiseStatus.Suspended)

        // Assert
        assertEquals(ModelFranchiseStatus.Active, resultActive)
        assertEquals(ModelFranchiseStatus.Inactive, resultInactive)
        assertEquals(ModelFranchiseStatus.Suspended, resultSuspended)
    }
}