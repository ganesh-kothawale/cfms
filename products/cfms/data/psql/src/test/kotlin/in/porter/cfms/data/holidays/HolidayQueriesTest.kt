package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidayMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidaysFranchiseRowMapper
import `in`.porter.cfms.data.holidays.mappers.UpdateHolidayRowMapper
import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.data.holidays.records.UpdateHolidayRecord
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.data.franchise.FranchisesTable
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
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
    private lateinit var updateMapper: UpdateHolidayRowMapper
    private lateinit var listHolidayMapper: ListHolidayMapper
    private lateinit var listHolidaysFranchiseMapper: ListHolidaysFranchiseRowMapper

    @BeforeAll
    fun setup() {
        postgresContainer = PostgreSQLContainer<Nothing>("postgres:14.13").apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
            start()
        }

        connectToDatabase()
        MockKAnnotations.init(this, relaxed = true)
        rowMapper = mockk(relaxed = true)
        updateMapper = mockk(relaxed = true)
        holidayQueries = mockk(relaxed = true)
        listHolidayMapper = mockk(relaxed = true)
        listHolidaysFranchiseMapper = mockk(relaxed = true)

        transaction(db) {
            SchemaUtils.create(HolidayTable)
            SchemaUtils.create(FranchisesTable)
        }

        holidayQueries = HolidayQueries(
            db,
            Dispatchers.Unconfined,
            rowMapper,
            updateMapper,
            listHolidayMapper,
            listHolidaysFranchiseMapper
        )
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
            FranchisesTable.deleteAll()
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
    fun `test record method`() = runBlocking {
        // Insert a test franchise record into FranchisesTable to satisfy the foreign key constraint
        insertFranchiseRecord("ABC12")
        // Now, insert the HolidayRecord
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

        // Act: Call the record method and get the generated ID
        val generatedId = holidayQueries.record(holidayRecord)

        // Assert: Check if the generated ID is greater than zero
        assertTrue(generatedId > 0, "The generated ID should be greater than zero.")
    }

    @Test
    fun `test get by franchiseId and date`(): Unit = runBlocking {
        insertFranchiseRecord("ABC12")
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
        insertFranchiseRecord("ABC12")
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

        insertFranchiseRecord("ABC12")
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
        val generatedId = holidayQueries.record(holidayRecord)

        // Update the record
        val updatedHoliday = UpdateHolidayRecord(
            holidayId = generatedId,
            franchiseId = "ABC12",
            startDate = LocalDate.of(2024, 10, 14),
            endDate = LocalDate.of(2024, 10, 15),
            holidayName = "Updated Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        val rowsUpdated = holidayQueries.updateHoliday(updatedHoliday)

        // Verify the update was successful
        assertEquals(1, rowsUpdated, "Expected exactly one row to be updated")
    }

    @Test
    fun `test find holidays without any filter`(): Unit = runBlocking {
        // Insert test data
        transaction(db) {
            insertFranchiseRecord("FRANCHISE1")
            insertHolidayRecord(
                "FRANCHISE1",
                LocalDate.of(2024, 12, 25),
                LocalDate.of(2024, 12, 26),
                "Christmas",
                LeaveType.Normal
            )
            insertHolidayRecord(
                "FRANCHISE1",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2),
                "New Year's Day",
                LeaveType.Emergency
            )
        }

        // Test with no filters
        val result = holidayQueries.findHolidays(null, null, null, null, page = 1, size = 10)
        assertEquals(2, result.size)
    }

    @Test
    fun `test find holidays with franchise filter`(): Unit = runBlocking {
        // Test with a specific franchise filter
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        val result = holidayQueries.findHolidays("FRANCHISE1", null, null, null, page = 1, size = 10)
        assertEquals(2, result.size)
        result.forEach { row ->
            assertEquals("FRANCHISE1", row[HolidayTable.franchiseId])
        }
    }

    @Test
    fun `test find holidays with leave type filter`(): Unit = runBlocking {
        // Test with a specific leave type filter
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        val result = holidayQueries.findHolidays(null, LeaveType.Normal, null, null, page = 1, size = 10)
        assertEquals(1, result.size)
        assertEquals("Christmas", result[0][HolidayTable.holidayName])
    }

    @Test
    fun `test find holidays with date range filter`(): Unit = runBlocking {
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        // Test with a date range filter
        val result = holidayQueries.findHolidays(
            null,
            null,
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31),
            page = 1,
            size = 10
        )
        assertEquals(2, result.size)
    }

    @Test
    fun `test find holidays with pagination`(): Unit = runBlocking {
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        // Test with pagination
        val firstPage = holidayQueries.findHolidays(null, null, null, null, page = 1, size = 1)
        val secondPage = holidayQueries.findHolidays(null, null, null, null, page = 2, size = 1)

        assertEquals(1, firstPage.size)
        assertEquals(1, secondPage.size)
        assertNotEquals(firstPage[0][HolidayTable.holidayName], secondPage[0][HolidayTable.holidayName])
    }

    @Test
    fun `test find holidays with all filters`(): Unit = runBlocking {
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        // Test with all filters applied
        val result = holidayQueries.findHolidays(
            franchiseId = "FRANCHISE1",
            leaveType = LeaveType.Normal,
            startDate = LocalDate.of(2024, 12, 1),
            endDate = LocalDate.of(2024, 12, 31),
            page = 1,
            size = 10
        )
        assertEquals(1, result.size)
        assertEquals("Christmas", result[0][HolidayTable.holidayName])
    }

    @Test
    fun `test count holidays without any filter`(): Unit = runBlocking {
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        // Count holidays without any filter
        val count = holidayQueries.countHolidays(null, null, null, null)
        assertEquals(2, count)
    }

    @Test
    fun `test count holidays with franchise filter`(): Unit = runBlocking {
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        // Count holidays for a specific franchise
        val count = holidayQueries.countHolidays("FRANCHISE1", null, null, null)
        assertEquals(2, count)
    }

    @Test
    fun `test count holidays with leave type filter`(): Unit = runBlocking {
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        // Count holidays with a specific leave type
        val count = holidayQueries.countHolidays(null, LeaveType.Normal, null, null)
        assertEquals(1, count)
    }

    @Test
    fun `test count holidays with date range filter`(): Unit = runBlocking {
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        // Count holidays within a date range
        val count = holidayQueries.countHolidays(
            franchiseId = null,
            leaveType = null,
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )
        assertEquals(2, count)
    }

    @Test
    fun `test count holidays with all filters`(): Unit = runBlocking {
        insertFranchiseRecord("FRANCHISE1")
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 26),
            "Christmas",
            LeaveType.Normal
        )
        insertHolidayRecord(
            "FRANCHISE1",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 2),
            "New Year's Day",
            LeaveType.Emergency
        )
        // Count holidays with all filters applied
        val count = holidayQueries.countHolidays(
            franchiseId = "FRANCHISE1",
            leaveType = LeaveType.Normal,
            startDate = LocalDate.of(2024, 12, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )
        assertEquals(1, count)
    }


}
