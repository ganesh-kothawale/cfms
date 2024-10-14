package `in`.porter.cfms.data.repos.usecases

import `in`.porter.cfms.data.franchise.FranchiseQueries
import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.franchise.mappers.FranchiseRowMapper
import `in`.porter.cfms.data.franchise.mappers.ListFranchisesRowMapper
import `in`.porter.cfms.data.franchise.records.FranchiseRecordData
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.time.Instant
import org.testcontainers.containers.PostgreSQLContainer

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FranchiseQueriesTest {

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var franchiseQueries: FranchiseQueries
    private lateinit var mapper: FranchiseRowMapper
    private lateinit var postgresContainer: PostgreSQLContainer<Nothing>
    private lateinit var db: Database
    private lateinit var listMapper: ListFranchisesRowMapper

    @BeforeAll
    fun setupDb() {
        postgresContainer = PostgreSQLContainer<Nothing>("postgres:14.13").apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
            start()
        }

        db = Database.connect(
            url = postgresContainer.jdbcUrl,
            driver = "org.postgresql.Driver",
            user = postgresContainer.username,
            password = postgresContainer.password
        )

        MockKAnnotations.init(this, relaxed = true)
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        transaction(db) {
            SchemaUtils.create(FranchisesTable)
        }

        mapper = mockk(relaxed = true)
        listMapper = mockk(relaxed = true)// Mock with relaxed behavior
        franchiseQueries = FranchiseQueries(db, Dispatchers.Unconfined, mapper, listMapper)
    }

    @BeforeEach
    fun resetDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun shouldSaveFranchiseRecord() = runTest {
        val franchiseData = FranchiseRecordData(
            franchiseId = "F123",
            address = "123 Main St",
            city = "Cityville",
            state = "State",
            pincode = "123456",
            pocName = "John Doe",
            primaryNumber = "1234567890",
            email = "franchise@example.com",
            status = "active",
            porterHubName = "Hub1",
            franchiseGst = "GST123",
            franchisePan = "PAN123",
            franchiseCanceledCheque = "Cheque123",
            daysOfOperation = "Mon-Fri",
            hlpEnabled = true,
            kamUser = "User1",
            showCrNumber = false,
            cutOffTime = "17",
            startTime = "9",
            endTime = "17",
            longitude = BigDecimal("0.0"),
            latitude = BigDecimal("0.0"),
            radiusCoverage = BigDecimal("0.0"),
            teamId = 1
        )

        val id = franchiseQueries.save(franchiseData)
        assertNotNull(id)
    }

    @Test
    fun shouldReturnFranchiseByEmail() = runTest {
        val franchiseData = FranchiseRecordData(
            franchiseId = "F123",
            address = "123 Main St",
            city = "Cityville",
            state = "State",
            pincode = "123456",
            pocName = "John Doe",
            primaryNumber = "1234567890",
            email = "franchise@example.com",
            status = "active",
            porterHubName = "Hub1",
            franchiseGst = "GST123",
            franchisePan = "PAN123",
            franchiseCanceledCheque = "Cheque123",
            daysOfOperation = "Mon-Fri",
            hlpEnabled = true,
            kamUser = "User1",
            showCrNumber = false,
            cutOffTime = "17",
            startTime = "9",
            endTime = "17",
            longitude = BigDecimal("0.0"),
            latitude = BigDecimal("0.0"),
            radiusCoverage = BigDecimal("0.0"),
            teamId = 1
        )

        franchiseQueries.save(franchiseData)

        val result = franchiseQueries.getByEmail("franchise@example.com")

        assertNotNull(result, "Expected a non-null result")
    }

    @Test
    fun shouldReturnNullForNonExistingFranchise() = runTest {
        val result = franchiseQueries.getByEmail("nonexistent@example.com")
        assertNull(result)
    }

    @Test
    fun shouldReturnFranchiseByCode() = runTest {
        val franchiseData = FranchiseRecordData(
            franchiseId = "F124",
            address = "456 Elm St",
            city = "Townsville",
            state = "State",
            pincode = "654321",
            pocName = "Jane Doe",
            primaryNumber = "0987654321",
            email = "franchise2@example.com",
            status = "active",
            porterHubName = "Hub2",
            franchiseGst = "GST456",
            franchisePan = "PAN456",
            franchiseCanceledCheque = "Cheque456",
            daysOfOperation = "Sat-Sun",
            hlpEnabled = false,
            kamUser = "User2",
            showCrNumber = true,
            cutOffTime = "17",
            startTime = "9",
            endTime = "17",
            longitude = BigDecimal("0.0"),
            latitude = BigDecimal("0.0"),
            radiusCoverage = BigDecimal("0.0"),
            teamId = 2
        )

        franchiseQueries.save(franchiseData)

        val result = franchiseQueries.getByCode("franchise2@example.com")
        assertNotNull(result, "Expected a non-null result")
    }

    @Test
    fun shouldReturnNullForNonExistingFranchiseByCode() = runTest {
        val result = franchiseQueries.getByCode("nonexistent@example.com")
        assertNull(result)
    }
}
