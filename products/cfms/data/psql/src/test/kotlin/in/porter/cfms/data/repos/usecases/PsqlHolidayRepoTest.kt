package `in`.porter.cfms.data.repos.usecases

import `in`.porter.cfms.data.holidays.HolidayQueries
import `in`.porter.cfms.data.holidays.mappers.HolidayMapper
import `in`.porter.cfms.data.holidays.mappers.UpdateHolidayMapper
import `in`.porter.cfms.data.repos.PsqlHolidayRepo
import `in`.porter.cfms.data.repos.factories.PsqlHolidayRepoFactory
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.Instant
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PsqlHolidayRepoTest {

    private lateinit var holidayRepo: PsqlHolidayRepo
    private lateinit var holidayQueries: HolidayQueries
    private lateinit var holidayMapper: HolidayMapper
    private lateinit var updateHolidayMapper: UpdateHolidayMapper

    @BeforeEach
    fun setup() {
        holidayQueries = mockk()
        holidayMapper = mockk()
        updateHolidayMapper = mockk()
        holidayRepo = PsqlHolidayRepo(holidayQueries, holidayMapper, updateHolidayMapper)

        // Mocking toRecord and toDomain with valid responses
        coEvery { holidayMapper.toRecord(any()) } returns mockk()
        coEvery { holidayMapper.toDomain(any()) } returns mockk()
        coEvery { updateHolidayMapper.toRecord(any()) } returns mockk()
        coEvery { updateHolidayMapper.toDomain(any()) } returns mockk()
    }

    @Test
    fun `should get holiday by franchise ID and date`() = runBlocking {
        val franchiseId = "123"
        val startDate = LocalDate.now()
        val endDate = LocalDate.now().plusDays(1)
        val holiday = PsqlHolidayRepoFactory.buildHoliday(franchiseId)

        coEvery { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) } returns mockk()
        coEvery { holidayMapper.toDomain(any()) } returns holiday

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNotNull(result)
        assertEquals(holiday, result)
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) }
        coVerify(exactly = 1) { holidayMapper.toDomain(any()) }
    }

    @Test
    fun `should record holiday and return ID`() = runBlocking {
        val holiday = PsqlHolidayRepoFactory.buildHoliday()
        val holidayRecordId = 1

        coEvery { holidayMapper.toRecord(holiday) } returns mockk()
        coEvery { holidayQueries.record(any()) } returns holidayRecordId

        val result = holidayRepo.record(holiday)

        assertEquals(holidayRecordId, result)
        coVerify(exactly = 1) { holidayMapper.toRecord(holiday) }
        coVerify(exactly = 1) { holidayQueries.record(any()) }
    }

    @Test
    fun `should return null if no holiday found by id and date`() = runBlocking {
        // Arrange
        coEvery {  holidayQueries.getByIdAndDate(any(), any(), any()) } returns null

        // Act
        val result = holidayRepo.getByIdAndDate("ABC12", LocalDate.now(), LocalDate.now().plusDays(1))

        // Assert
        assertNull(result)
        coVerify { holidayQueries.getByIdAndDate(any(), any(), any()) }
    }

    @Test
    fun `should return null for invalid start date greater than end date`() = runBlocking {
        // Arrange
        val franchiseId = "ABC12"
        val invalidStartDate = LocalDate.of(2024, 9, 27)
        val endDate = LocalDate.of(2024, 9, 26)

        coEvery { holidayQueries.getByIdAndDate(franchiseId, invalidStartDate, endDate) } returns null

        // Act
        val result = holidayRepo.getByIdAndDate(franchiseId, invalidStartDate, endDate)

        // Assert
        assertNull(result)
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, invalidStartDate, endDate) }
    }

    @Test
    fun `should return a holiday when valid data is found for franchise and dates`() = runBlocking {
        val franchiseId = "ABC12"
        val startDate = LocalDate.of(2024, 9, 26)
        val endDate = LocalDate.of(2024, 9, 27)
        val holidayRecord = PsqlHolidayRepoFactory.buildHoliday(franchiseId)

        coEvery { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) } returns mockk() // Mocked record from queries
        coEvery { holidayMapper.toDomain(any()) } returns holidayRecord // Mocked domain response

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNotNull(result)
        assertEquals(holidayRecord, result)
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) }
        coVerify(exactly = 1) { holidayMapper.toDomain(any()) }
    }

    @Test
    fun `should throw exception when failing to record a holiday`() = runBlocking {
        val holiday = PsqlHolidayRepoFactory.buildHoliday()

        coEvery { holidayMapper.toRecord(any()) } returns mockk() // Mocked response for toRecord
        coEvery { holidayQueries.record(any()) } throws Exception("DB write failure") // Mock exception for record

        val exception = assertThrows(Exception::class.java) {
            runBlocking { holidayRepo.record(holiday) }
        }

        assertEquals("DB write failure", exception.message)
        coVerify(exactly = 1) { holidayMapper.toRecord(any()) }
        coVerify(exactly = 1) { holidayQueries.record(any()) }
    }



    @Test
    fun `should throw IllegalArgumentException when holiday has invalid fields`() = runBlocking {
        val invalidHoliday = Holiday(
            franchiseId = "",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().minusDays(1), // Invalid end date
            holidayName = null,
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        coEvery { holidayMapper.toRecord(any()) } throws IllegalArgumentException("Invalid holiday data")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            runBlocking { holidayRepo.record(invalidHoliday) }
        }

        assertEquals("Invalid holiday data", exception.message)
        coVerify(exactly = 1) { holidayMapper.toRecord(any()) }
    }

    @Test
    fun `should throw different exception on record failure`() = runBlocking {
        val holiday = PsqlHolidayRepoFactory.buildHoliday()

        coEvery { holidayMapper.toRecord(any()) } returns mockk()
        coEvery { holidayQueries.record(any()) } throws RuntimeException("Unexpected error")

        val exception = assertThrows(RuntimeException::class.java) {
            runBlocking { holidayRepo.record(holiday) }
        }

        assertEquals("Unexpected error", exception.message)
        coVerify(exactly = 1) { holidayMapper.toRecord(any()) }
        coVerify(exactly = 1) { holidayQueries.record(any()) }
    }

    @Test
    fun `should return holiday when start date equals end date`() = runBlocking {
        val franchiseId = "ABC12"
        val startDate = LocalDate.now()
        val holiday = PsqlHolidayRepoFactory.buildHoliday(franchiseId)

        coEvery { holidayQueries.getByIdAndDate(franchiseId, startDate, startDate) } returns mockk()
        coEvery { holidayMapper.toDomain(any()) } returns holiday

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, startDate)

        assertNotNull(result)
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, startDate, startDate) }
    }

    @Test
    fun `should return null if no holiday is returned by queries`() = runBlocking {
        val franchiseId = "ABC12"
        val startDate = LocalDate.now()
        val endDate = LocalDate.now().plusDays(1)

        coEvery { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) } returns null

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNull(result) // Ensure null is returned when no result from queries
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) }
        coVerify(exactly = 0) { holidayMapper.toDomain(any()) }
    }

    @Test
    fun `should return default holiday if queries return null`() = runBlocking {
        val franchiseId = "ABC12"
        val startDate = LocalDate.now()
        val endDate = LocalDate.now().plusDays(1)

        val defaultHoliday = Holiday(
            franchiseId = "default",
            startDate = startDate,
            endDate = endDate,
            holidayName = "Default Holiday",
            leaveType = LeaveType.Normal,
            backupFranchiseIds = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        coEvery { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) } returns mockk()
        coEvery { holidayMapper.toDomain(any()) } returns defaultHoliday

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNotNull(result)
        assertEquals(defaultHoliday, result)
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) }
        coVerify(exactly = 1) { holidayMapper.toDomain(any()) }
    }
    @Test
    fun `should handle exception when mapper fails`() = runBlocking {
        val holiday = PsqlHolidayRepoFactory.buildHoliday()

        coEvery { holidayMapper.toRecord(any()) } throws IllegalStateException("Mapper failed")

        val exception = assertThrows(IllegalStateException::class.java) {
            runBlocking { holidayRepo.record(holiday) }
        }

        assertEquals("Mapper failed", exception.message)
        coVerify(exactly = 1) { holidayMapper.toRecord(any()) }
    }

    @Test
    fun `should update holiday`() = runBlocking {
        val updateHolidayEntity = PsqlHolidayRepoFactory.buildUpdateHolidayEntity()

        coEvery { updateHolidayMapper.toRecord(any()) } returns mockk()
        coEvery { holidayQueries.updateHoliday(any()) } returns 1

        holidayRepo.update(updateHolidayEntity)

        coVerify(exactly = 1) { updateHolidayMapper.toRecord(updateHolidayEntity) }
        coVerify(exactly = 1) { holidayQueries.updateHoliday(any()) }
    }

    @Test
    fun `should delete holiday by ID`() = runBlocking {
        val holidayId = 1

        coEvery { holidayQueries.deleteHoliday(holidayId) } returns 1

        holidayRepo.deleteById(holidayId)

        coVerify(exactly = 1) { holidayQueries.deleteHoliday(holidayId) }
    }
}
