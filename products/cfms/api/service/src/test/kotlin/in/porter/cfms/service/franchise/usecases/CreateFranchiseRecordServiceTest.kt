package `in`.porter.cfms.service.franchise.usecases

import `in`.porter.cfms.api.models.FranchiseStatus
import `in`.porter.cfms.api.models.franchises.*
import `in`.porter.cfms.api.service.exceptions.CfmsException
import `in`.porter.cfms.api.service.franchises.mappers.RecordFranchiseDetailsRequestMapper
import `in`.porter.cfms.api.service.franchises.usecases.CreateFranchiseRecordService
import `in`.porter.cfms.domain.usecases.external.RecordFranchiseDetails
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateFranchiseRecordServiceTest {

    private lateinit var createFranchiseRecordService: CreateFranchiseRecordService
    private lateinit var mapper: RecordFranchiseDetailsRequestMapper
    private lateinit var recordFranchiseDetails: RecordFranchiseDetails
    @BeforeAll
    fun setUp() {
        mapper = mockk()
        recordFranchiseDetails = mockk()
        createFranchiseRecordService = CreateFranchiseRecordService(mapper, recordFranchiseDetails)
    }

    @Test
    fun `invoke should create franchise successfully`() = runBlockingTest {
        // Prepare test data
        val generatedFranchiseId = UUID.randomUUID().toString().take(10)
        val request = RecordFranchiseDetailsRequest(
            address = RecordFranchiseAddressRequest(
                address = "123 Main St",
                city = "Cityville",
                state = "State",
                pincode = "123456",
                latitude = BigDecimal("12.34"),
                longitude = BigDecimal("56.78")
            ),
            poc = RecordFranchisePOCRequest(
                name = "John Doe",
                primaryNumber = "9876543210",
                email = "john.doe@example.com"
            ),
            customerShipmentLabelRequired = true,
            radiusCoverage = BigDecimal("10"),
            hlpEnabled = true,
            status = FranchiseStatus.Active,
            daysOfOperation = "Mon-Fri",
            cutOffTime = Instant.now(),
            startTime = Instant.now(),
            endTime = Instant.now(),
            porterHubName = "Hub A",
            franchiseGst = "GST123",
            franchisePan = "PAN123",
            franchiseCanceledCheque = "Cheque123",
            kamUser = "KAMUser",
            showCrNumber = true
        )

        // Mock behavior for the service's invoke method
        val expectedResponse = RecordFranchiseDetailsResponse(
            data = Data(
                message = "Franchise created successfully",
                franchise_id = generatedFranchiseId
            )
        )

        // Mocking the invoke method of the createFranchiseRecordService
        coEvery { createFranchiseRecordService.invoke(any()) } returns expectedResponse

    }

    @Test
    fun `invoke should return error response for CfmsException`() = runBlockingTest {
        // Prepare test data
        val request = RecordFranchiseDetailsRequest(
            address = RecordFranchiseAddressRequest(
                address = "123 Main St",
                city = "Cityville",
                state = "State",
                pincode = "123456",
                latitude = BigDecimal("12.34"),
                longitude = BigDecimal("56.78")
            ),
            poc = RecordFranchisePOCRequest(
                name = "John Doe",
                primaryNumber = "9876543210",
                email = "john.doe@example.com"
            ),
            customerShipmentLabelRequired = true,
            radiusCoverage = BigDecimal("10"),
            hlpEnabled = true,
            status = FranchiseStatus.Active,
            daysOfOperation = "Mon-Fri",
            cutOffTime = Instant.now(),
            startTime = Instant.now(),
            endTime = Instant.now(),
            porterHubName = "Hub A",
            franchiseGst = "GST123",
            franchisePan = "PAN123",
            franchiseCanceledCheque = "Cheque123",
            kamUser = "KAMUser",
            showCrNumber = true
        )

        // Mock behavior to throw CfmsException
        every { mapper.toDomain(any(), any()) } throws CfmsException("Invalid input data")

        // Invoke the service
        val response = createFranchiseRecordService.invoke(request)

        // Verify the results
        verify(exactly = 1) { mapper.toDomain(request, any()) }

        assert(response.error.isNotEmpty())
        assert(response.error[0].message == "Invalid input data")
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }
}
