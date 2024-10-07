package `in`.porter.cfms.data.repos

import `in`.porter.cfms.data.factories.FranchiseFactory
import `in`.porter.cfms.data.franchise.FranchiseQueries
import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.franchise.mappers.FranchiseRecordMapper
import `in`.porter.cfms.data.franchise.mappers.FranchiseRowMapper
import `in`.porter.cfms.data.franchise.records.FranchiseRecord
import `in`.porter.cfms.data.franchise.records.FranchiseRecordData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.Instant

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PsqlFranchisesRepoTest {

    private lateinit var psqlFranchisesRepo: PsqlFranchisesRepo
    private lateinit var queries: FranchiseQueries
    private lateinit var mapper: FranchiseRecordMapper
    private lateinit var franchiseRecordFactory: FranchiseFactory
    private lateinit var franchiseRecordMapper: FranchiseRecordMapper
    private lateinit var franchiseRowMapper: FranchiseRowMapper

    @BeforeEach
    fun setup() {
        queries = mockk()
        mapper = FranchiseRecordMapper()
        franchiseRowMapper = FranchiseRowMapper()
        psqlFranchisesRepo = PsqlFranchisesRepo(queries, mapper)
        franchiseRecordFactory = FranchiseFactory() // Initialize the factory
    }
    @Test
    fun `should create a franchise and call save on queries`() = runBlocking {
        // Arrange
        val franchise = franchiseRecordFactory.create(
            franchiseId = "test_id",
            pocName = "Test POC",
            primaryNumber = "9876543210",
            email = "test@example.com"
        )

        // Mock the save behavior of queries
        coEvery { queries.save(any()) } returns 1

        // Act
        psqlFranchisesRepo.create(franchise)

        // Assert
        coVerify(exactly = 1) { queries.save(mapper.toRecord(franchise)) }
    }

    @Test
    fun `should get franchise by email and call queries`() = runBlocking {
        // Arrange
        // Create a mock ResultRow with necessary fields populated
        val mockResultRow = mockk<ResultRow>(relaxed = true)

        // Define the expected fields in the ResultRow based on the franchises table structure
        every { mockResultRow[FranchisesTable.franchiseId] } returns "some_code"
        every { mockResultRow[FranchisesTable.createdAt] } returns Instant.now()
        every { mockResultRow[FranchisesTable.updatedAt] } returns Instant.now()
        every { mockResultRow[FranchisesTable.address] } returns "123 Test St"
        every { mockResultRow[FranchisesTable.city] } returns "Test City"
        every { mockResultRow[FranchisesTable.state] } returns "Test State"
        every { mockResultRow[FranchisesTable.pincode] } returns "123456"
        every { mockResultRow[FranchisesTable.latitude] } returns BigDecimal.ZERO // Assuming latitude is 0.0
        every { mockResultRow[FranchisesTable.longitude] } returns BigDecimal.ZERO // Assuming longitude is 0.0
        every { mockResultRow[FranchisesTable.pocName] } returns "Test POC"
        every { mockResultRow[FranchisesTable.primaryNumber] } returns "9876543210"
        every { mockResultRow[FranchisesTable.email] } returns "test@example.com"
        every { mockResultRow[FranchisesTable.status] } returns "Active"
        every { mockResultRow[FranchisesTable.porterHubName] } returns "Main Hub"
        every { mockResultRow[FranchisesTable.franchiseGst] } returns "GST123456789"
        every { mockResultRow[FranchisesTable.franchisePan] } returns "PAN123456"
        every { mockResultRow[FranchisesTable.franchiseCanceledCheque] } returns "Cheque Image URL"
        every { mockResultRow[FranchisesTable.daysOfOperation] } returns "Mon-Fri"
        every { mockResultRow[FranchisesTable.startTime] } returns Instant.now()
        every { mockResultRow[FranchisesTable.endTime] } returns Instant.now()
        every { mockResultRow[FranchisesTable.cutOffTime] } returns Instant.now()
        every { mockResultRow[FranchisesTable.hlpEnabled] } returns true
        every { mockResultRow[FranchisesTable.radiusCoverage] } returns BigDecimal("5.0")
        every { mockResultRow[FranchisesTable.showCrNumber] } returns true
        every { mockResultRow[FranchisesTable.teamId] } returns 1 // Assuming a team ID of 1
        every { mockResultRow[FranchisesTable.kamUser] } returns "KAM User"

        // Create an expected FranchiseRecordData object
        val expectedFranchiseData = FranchiseRecordData(
            franchiseId = "some_code",
            address = "123 Test St",
            city = "Test City",
            state = "Test State",
            pincode = "123456",
            latitude = BigDecimal.ZERO, // Set to a valid value
            longitude = BigDecimal.ZERO, // Set to a valid value
            pocName = "Test POC",
            primaryNumber = "9876543210",
            email = "test@example.com",
            status = "Active", // Set to a valid value
            porterHubName = "Main Hub", // Set to a valid value
            franchiseGst = "GST123456789", // Set to a valid value
            franchisePan = "PAN123456", // Set to a valid value
            franchiseCanceledCheque = "Cheque Image URL", // Set to a valid value
            daysOfOperation = "Mon-Fri", // Set to a valid value
            startTime = Instant.now(), // Set to a valid value
            endTime = Instant.now(), // Set to a valid value
            cutOffTime = Instant.now(), // Set to a valid value
            hlpEnabled = true, // Set to a valid value
            radiusCoverage = BigDecimal("5.0"), // Set to a valid value
            showCrNumber = true, // Set to a valid value
            kamUser = "KAM User", // Set to a valid value
            teamId = 1 // Set to a valid value
        )

        // Create the expected FranchiseRecord object
        val expectedFranchise = FranchiseRecord(
            franchiseId = "some_code",
            createdAt = Instant.now(), // Match createdAt set in ResultRow
            updatedAt = Instant.now(), // Match updatedAt set in ResultRow
            data = expectedFranchiseData
        )

        // Mock the behavior of queries.getByEmail to return the mock ResultRow
        coEvery { queries.getByEmail(any()) } returns franchiseRowMapper.toRecord(mockResultRow)

        // Act
        val result = psqlFranchisesRepo.getByEmail("test@example.com") // Assuming this looks up by email

        // Assert
        coVerify(exactly = 1) { queries.getByEmail("test@example.com") }

        // Assert that the result matches the expected FranchiseRecord
        assert(result?.franchiseId == expectedFranchise.franchiseId) { "Expected franchiseId does not match the result." }
    }



    @Test
    fun `should return null if franchise does not exist`() = runBlocking {
        // Arrange
        coEvery { queries.getByCode(any()) } returns null

        // Act
        val result = psqlFranchisesRepo.getByCode("non_existent_code")

        // Assert
        coVerify(exactly = 1) { queries.getByCode("non_existent_code") }
        // Check that the result is null
        assert(result == null) { "Expected result to be null but was $result." }
    }

    @Test
    fun `should throw exception when creating franchise fails`() = runBlocking {
        // Arrange
        val franchise = franchiseRecordFactory.create(franchiseId = "error_test_id", pocName = "Error POC")
        coEvery { queries.save(any()) } throws Exception("DB error")

        // Act & Assert
        val exception = assertThrows<RuntimeException> {
            psqlFranchisesRepo.create(franchise)
        }

        assert(exception.message == "Failed to create franchise: DB error") { "Expected error message did not match." }
        coVerify(exactly = 1) { queries.save(franchiseRecordMapper.toRecord(franchise)) }
    }
}
