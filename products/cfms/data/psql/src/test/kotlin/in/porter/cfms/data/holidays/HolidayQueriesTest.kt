package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidayMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidaysFranchiseRowMapper
import `in`.porter.cfms.data.holidays.mappers.UpdateHolidayRowMapper
import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.data.holidays.records.UpdateHolidayRecord
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.testcontainers.containers.PostgreSQLContainer
import java.time.Instant
import java.time.LocalDate

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HolidayQueriesTest {

    private lateinit var postgresContainer: PostgreSQLContainer<Nothing>
    private lateinit var db: Database
    private lateinit var holidayQueries: HolidayQueries
    private lateinit var rowMapper: HolidayRowMapper
    private lateinit var updateRowMapper: UpdateHolidayRowMapper
    private lateinit var listHolidayMapper: ListHolidayMapper
    private lateinit var listHolidaysFranchiseMapper: ListHolidaysFranchiseRowMapper

    @BeforeAll
    fun setup() {
        postgresContainer = PostgreSQLContainer<Nothing>("postgres:13.3").apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
            start()
        }

        connectToDatabase()
        MockKAnnotations.init(this, relaxed = true)
        rowMapper = mockk(relaxed = true)
        updateRowMapper = mockk(relaxed = true)
        holidayQueries = mockk(relaxed = true)
        listHolidayMapper = mockk(relaxed = true)
        listHolidaysFranchiseMapper = mockk(relaxed = true)

        transaction(db) {
            SchemaUtils.create(HolidayTable)
            SchemaUtils.create(FranchisesTable)
        }

        transaction {
            // Insert a test franchise record into FranchisesTable
            FranchisesTable.insert {
                it[franchiseId] = "ABC12"  // Test franchise ID
                it[address] = "123 Test St"
                it[city] = "Test City"
                it[state] = "Test State"
                it[pincode] = "123456"
                it[latitude] = 12.345678.toBigDecimal()  // Example latitude
                it[longitude] = 98.765432.toBigDecimal()  // Example longitude
                it[pocName] = "John Doe"
                it[primaryNumber] = "1234567890"
                it[email] = "test@franchise.com"
                it[status] = "Active"
                it[porterHubName] = "HubName"
                it[franchiseGst] = "GST1234"
                it[franchisePan] = "PAN5678"
                it[franchiseCanceledCheque] = "ChequeImageURL"
                it[daysOfOperation] = "Mon-Fri"
                it[startTime] = Instant.now()  // Set start time as current timestamp
                it[endTime] = Instant.now().plusSeconds(3600)  // Example end time, 1 hour after start
                it[cutOffTime] = Instant.now().plusSeconds(1800)  // Example cutoff time, 30 minutes after start
                it[hlpEnabled] = true
                it[radiusCoverage] = 10.00.toBigDecimal()  // Example radius coverage
                it[showCrNumber] = false
                it[createdAt] = Instant.now()  // Set current timestamp as created_at
                it[updatedAt] = Instant.now()  // Set current timestamp as updated_at
                it[kamUser] = "KamUser"
                it[teamId] = 1  // Example team ID
            }
        }

        holidayQueries = HolidayQueries(db, Dispatchers.Unconfined, rowMapper, updateRowMapper, listHolidayMapper, listHolidaysFranchiseMapper)
    }

    @BeforeEach
    fun beforeEachTest() {
        // Reconnect to the database before each test to avoid closed connection issues
        if (!postgresContainer.isRunning) {
            postgresContainer.start()
        }
        connectToDatabase()
        transaction(db) {
            HolidayTable.deleteAll()
        }
    }

    @AfterAll
    fun teardown() {
        postgresContainer.stop()  // Stop the container after all tests are completed
    }

    private fun connectToDatabase() {
        db = Database.connect(
            url = postgresContainer.jdbcUrl,
            driver = "org.postgresql.Driver",
            user = postgresContainer.username,
            password = postgresContainer.password
        )
    }

    @Test
    fun `test record method`(): Unit = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "ABC12",
            startDate = LocalDate.of(2024, 10, 26),
            endDate = LocalDate.of(2024, 10, 27),
            holidayName = "Test Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        holidayQueries.record(holidayRecord)

        // Fetch the record back and assert it was inserted
        val insertedHoliday = holidayQueries.getByIdAndDate("ABC12", LocalDate.of(2024, 10, 26), LocalDate.of(2024, 10, 27))
        assertNotNull(insertedHoliday)
        assertEquals("ABC12", insertedHoliday?.franchiseId)
    }

    @Test
    fun `test get by franchiseId and date`(): Unit = runBlocking {
        // Insert a sample record to fetch
        val holidayRecord = HolidayRecord(
            franchiseId = "ABC12",
            startDate = LocalDate.of(2024, 11, 26),
            endDate = LocalDate.of(2024, 11, 27),
            holidayName = "Sample Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        holidayQueries.record(holidayRecord)

        // Now test fetching it
        val result = holidayQueries.getByIdAndDate("ABC12", LocalDate.of(2024, 11, 26), LocalDate.of(2024, 11, 27))
        assertNotNull(result)
        assertEquals("ABC12", result?.franchiseId)
    }

    @Test
    fun `test record with invalid dates throws exception`(): Unit = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "ABC12",
            startDate = LocalDate.of(2024, 9, 28),
            endDate = LocalDate.of(2024, 9, 26),  // Invalid date range
            holidayName = "Invalid Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        assertThrows<IllegalArgumentException> {
            runBlocking {
                holidayQueries.record(holidayRecord)
            }
        }
    }

    @Test
    fun `test record with missing required fields`(): Unit = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "",  // Missing franchiseId
            startDate = LocalDate.of(2024, 9, 26),
            endDate = LocalDate.of(2024, 9, 27),
            holidayName = null,
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        assertThrows<Exception> {
            runBlocking {
                holidayQueries.record(holidayRecord)
            }
        }
    }

    @Test
    fun `test get by franchiseId and non-existent date returns null`(): Unit = runBlocking {
        val result = holidayQueries.getByIdAndDate("ABC12", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 2))
        assertNull(result)
    }

    @Test
    fun `test record method with null backupFranchiseIds`() = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "ABC12",
            startDate = LocalDate.of(2024, 10, 1),
            endDate = LocalDate.of(2024, 10, 2),
            holidayName = "Null Backup Franchise Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,  // Explicitly set to null
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        holidayQueries.record(holidayRecord)

        val result = holidayQueries.getByIdAndDate("ABC12", LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 2))
        assertNotNull(result)
        assertTrue(result?.backupFranchiseIds.isNullOrEmpty())  // Check if null or empty
    }

    @Test
    fun `test update holiday`(): Unit = runBlocking {
        // Insert a record to update
        val holidayRecord = HolidayRecord(
            franchiseId = "ABC12",
            startDate = LocalDate.of(2024, 10, 14),
            endDate = LocalDate.of(2024, 10, 15),
            holidayName = "Test Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        holidayQueries.record(holidayRecord)

        // Update the record
        val updatedHoliday = UpdateHolidayRecord(
            holidayId = 1,
            franchiseId = "ABC12",
            startDate = LocalDate.of(2024, 10, 14),
            endDate = LocalDate.of(2024, 10, 15),
            holidayName = "Updated Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        holidayQueries.updateHoliday(updatedHoliday)

        // Verify the update
        val result = holidayQueries.getByIdAndDate("ABC12", LocalDate.of(2024, 10, 14), LocalDate.of(2024, 10, 15))
        assertNotNull(result)
        assertEquals("Updated Holiday", result?.holidayName)
    }
}
