package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.data.holidays.mappers.HolidayRowMapper
import `in`.porter.cfms.data.holidays.mappers.UpdateHolidayRowMapper
import `in`.porter.cfms.data.holidays.records.HolidayRecord
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
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

    @BeforeAll
    fun setup() {
        postgresContainer = PostgreSQLContainer<Nothing>("postgres:13.3").apply {
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
        rowMapper = mockk(relaxed = true)
        updateRowMapper = mockk(relaxed = true)

        transaction(db) {
            SchemaUtils.create(HolidayTable)
        }

        holidayQueries = HolidayQueries(db, Dispatchers.Unconfined, rowMapper, updateRowMapper)
    }

    @Test
    fun `test record method`(): Unit = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "F001",
            startDate = LocalDate.of(2024, 9, 26),
            endDate = LocalDate.of(2024, 9, 27),
            holidayName = "Test Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        holidayQueries.record(holidayRecord)
    }

    @Test
    fun `test get by franchiseId and date`(): Unit = runBlocking {
        val franchiseId = "F001"
        val startDate = LocalDate.of(2024, 9, 26)
        val endDate = LocalDate.of(2024, 9, 27)
        holidayQueries.getByIdAndDate(franchiseId, startDate, endDate)
    }

    @Test
    fun `test record with invalid dates throws exception`(): Unit = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "F002",
            startDate = LocalDate.of(2024, 9, 28),
            endDate = LocalDate.of(2024, 9, 26),  // Invalid date range
            holidayName = "Invalid Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        // Ensure an exception or error is thrown for invalid date range
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

        // Check that a validation or database error is thrown
        assertThrows<Exception> {
            runBlocking {
                holidayQueries.record(holidayRecord)
            }
        }
    }

    @Test
    fun `test get by franchiseId and non-existent date returns null`(): Unit = runBlocking {
        val franchiseId = "F001"
        val nonExistentStartDate = LocalDate.of(2025, 1, 1)
        val nonExistentEndDate = LocalDate.of(2025, 1, 2)

        val result = holidayQueries.getByIdAndDate(franchiseId, nonExistentStartDate, nonExistentEndDate)

        // Assert that the result is null
        assertNull(result)
    }

    @Test
    fun `test record method with null backupFranchiseIds`() = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "F004",
            startDate = LocalDate.of(2024, 10, 1),
            endDate = LocalDate.of(2024, 10, 2),
            holidayName = "Null Backup Franchise Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,  // Explicitly set to null
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        holidayQueries.record(holidayRecord)

        val result = holidayQueries.getByIdAndDate("F004", LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 2))
        assertNotNull(result)
        assertTrue(result?.backupFranchiseIds.isNullOrEmpty())  // Check if null or empty
    }

    @Test
    fun `test record method with empty backupFranchiseIds`() = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "F005",
            startDate = LocalDate.of(2024, 10, 3),
            endDate = LocalDate.of(2024, 10, 4),
            holidayName = "Empty Backup Franchise Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = "",  // Set to empty string
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        holidayQueries.record(holidayRecord)

        val result = holidayQueries.getByIdAndDate("F005", LocalDate.of(2024, 10, 3), LocalDate.of(2024, 10, 4))
        assertNotNull(result)
        assertTrue(result?.backupFranchiseIds.isNullOrEmpty())  // Check if null or empty
    }

    @Test
    fun `test record method with missing backupFranchiseIds`() = runBlocking {
        val holidayRecord = HolidayRecord(
            franchiseId = "F007",
            startDate = LocalDate.of(2024, 10, 7),
            endDate = LocalDate.of(2024, 10, 8),
            holidayName = "No Backup Franchise Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,  // Backup franchise IDs not set
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        holidayQueries.record(holidayRecord)

        val result = holidayQueries.getByIdAndDate("F007", LocalDate.of(2024, 10, 7), LocalDate.of(2024, 10, 8))
        assertNotNull(result)
        assertTrue(result?.backupFranchiseIds.isNullOrEmpty())  // Check if null or empty
    }


}
