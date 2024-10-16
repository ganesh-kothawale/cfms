//package `in`.porter.cfms.data.repos.usecases
//
//import `in`.porter.cfms.data.factories.FranchiseFactory
//import `in`.porter.cfms.data.franchise.FranchiseQueries
//import `in`.porter.cfms.data.franchise.FranchisesTable
//import `in`.porter.cfms.data.franchise.mappers.FranchiseRecordMapper
//import `in`.porter.cfms.data.franchise.mappers.FranchiseRowMapper
//import `in`.porter.cfms.data.franchise.mappers.ListFranchisesMapper
//import `in`.porter.cfms.data.franchise.mappers.ListFranchisesRowMapper
//import `in`.porter.cfms.data.franchise.mappers.UpdateFranchiseRecordMapper
//import `in`.porter.cfms.data.franchise.records.FranchiseRecord
//import `in`.porter.cfms.data.franchise.records.FranchiseRecordData
//import `in`.porter.cfms.data.franchise.repos.PsqlFranchisesRepo
//import io.mockk.*
//import kotlinx.coroutines.runBlocking
//import org.jetbrains.exposed.sql.ResultRow
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import java.math.BigDecimal
//import java.time.Instant
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class PsqlFranchisesRepoTest {
//
//    private lateinit var psqlFranchisesRepo: PsqlFranchisesRepo
//    private lateinit var queries: FranchiseQueries
//    private lateinit var mapper: FranchiseRecordMapper
//    private lateinit var franchiseFactory: FranchiseFactory
//    private lateinit var franchiseRowMapper: FranchiseRowMapper
//    private lateinit var listMapper: ListFranchisesMapper
//    private lateinit var listRowMapper: ListFranchisesRowMapper
//    private lateinit var updatemapper: UpdateFranchiseRecordMapper
//
//    @BeforeEach
//    fun setup() {
//        queries = mockk()
//        mapper = FranchiseRecordMapper()
//        listMapper = ListFranchisesMapper()
//        franchiseRowMapper = FranchiseRowMapper()
//        psqlFranchisesRepo = PsqlFranchisesRepo(queries, mapper, listMapper,updatemapper)
//        franchiseFactory = FranchiseFactory()
//    }
//
//    @Test
//    fun `should create a franchise and call save on queries`() = runBlocking {
//        // Arrange
//        val franchise = franchiseFactory.create(
//            franchiseId = "test_id",
//            pocName = "Test POC",
//            primaryNumber = "9876543210",
//            email = "test@example.com"
//        )
//
//        coEvery { queries.save(any()) } returns 1
//
//        // Act
//        psqlFranchisesRepo.create(franchise)
//
//        // Assert
//        coVerify(exactly = 1) { queries.save(mapper.toRecord(franchise)) }
//    }
//
//    @Test
//    fun `should get franchise by email and call queries`() = runBlocking {
//        // Arrange
//        val mockResultRow = mockk<ResultRow>(relaxed = true)
//
//        // Mocking ResultRow with expected values
//        every { mockResultRow[FranchisesTable.franchiseId] } returns "some_code"
//        every { mockResultRow[FranchisesTable.createdAt] } returns Instant.now()
//        every { mockResultRow[FranchisesTable.updatedAt] } returns Instant.now()
//        every { mockResultRow[FranchisesTable.address] } returns "123 Test St"
//        every { mockResultRow[FranchisesTable.city] } returns "Test City"
//        every { mockResultRow[FranchisesTable.state] } returns "Test State"
//        every { mockResultRow[FranchisesTable.pincode] } returns "123456"
//        every { mockResultRow[FranchisesTable.latitude] } returns BigDecimal.ZERO
//        every { mockResultRow[FranchisesTable.longitude] } returns BigDecimal.ZERO
//        every { mockResultRow[FranchisesTable.pocName] } returns "Test POC"
//        every { mockResultRow[FranchisesTable.primaryNumber] } returns "9876543210"
//        every { mockResultRow[FranchisesTable.email] } returns "test@example.com"
//        every { mockResultRow[FranchisesTable.status] } returns "Active"
//        every { mockResultRow[FranchisesTable.porterHubName] } returns "Main Hub"
//        every { mockResultRow[FranchisesTable.franchiseGst] } returns "GST123456789"
//        every { mockResultRow[FranchisesTable.franchisePan] } returns "PAN123456"
//        every { mockResultRow[FranchisesTable.franchiseCanceledCheque] } returns "Cheque Image URL"
//        every { mockResultRow[FranchisesTable.daysOfOperation] } returns "Mon-Fri"
//        every { mockResultRow[FranchisesTable.startTime] } returns "17"
//        every { mockResultRow[FranchisesTable.endTime] } returns "9"
//        every { mockResultRow[FranchisesTable.cutOffTime] } returns "17"
//        every { mockResultRow[FranchisesTable.startTime] } returns "09:00"
//        every { mockResultRow[FranchisesTable.endTime] } returns "17:00"
//        every { mockResultRow[FranchisesTable.cutOffTime] } returns "13:00"
//        every { mockResultRow[FranchisesTable.hlpEnabled] } returns true
//        every { mockResultRow[FranchisesTable.radiusCoverage] } returns BigDecimal("5.0")
//        every { mockResultRow[FranchisesTable.showCrNumber] } returns true
//        every { mockResultRow[FranchisesTable.teamId] } returns 1
//        every { mockResultRow[FranchisesTable.kamUser] } returns "KAM User"
//
//        val expectedFranchiseData = FranchiseRecordData(
//            franchiseId = "some_code",
//            address = "123 Test St",
//            city = "Test City",
//            state = "Test State",
//            pincode = "123456",
//            latitude = BigDecimal.ZERO,
//            longitude = BigDecimal.ZERO,
//            pocName = "Test POC",
//            primaryNumber = "9876543210",
//            email = "test@example.com",
//            status = "Active",
//            porterHubName = "Main Hub",
//            franchiseGst = "GST123456789",
//            franchisePan = "PAN123456",
//            franchiseCanceledCheque = "Cheque Image URL",
//            daysOfOperation = "Mon-Fri",
//            cutOffTime = "17",
//            startTime = "9",
//            endTime = "17",
//            hlpEnabled = true,
//            radiusCoverage = BigDecimal("5.0"),
//            showCrNumber = true,
//            kamUser = "KAM User",
//            teamId = 1
//        )
//
//        val expectedFranchise = FranchiseRecord(
//            franchiseId = "some_code",
//            createdAt = Instant.now(),
//            updatedAt = Instant.now(),
//            data = expectedFranchiseData
//        )
//
//        coEvery { queries.getByEmail(any()) } returns franchiseRowMapper.toRecord(mockResultRow)
//
//        // Act
//        val result = psqlFranchisesRepo.getByEmail("test@example.com")
//
//        // Assert
//        coVerify(exactly = 1) { queries.getByEmail("test@example.com") }
//        assertEquals(expectedFranchise.franchiseId, result?.franchiseId)
//    }
//
//    @Test
//    fun `should return null if franchise does not exist`() = runBlocking {
//        coEvery { queries.getByCode(any()) } returns null
//
//        val result = psqlFranchisesRepo.getByCode("non_existent_code")
//
//        coVerify(exactly = 1) { queries.getByCode("non_existent_code") }
//        assert(result == null) { "Expected result to be null but was $result." }
//    }
//
//}
