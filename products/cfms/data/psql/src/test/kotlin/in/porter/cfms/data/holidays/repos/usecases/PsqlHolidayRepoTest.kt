package `in`.porter.cfms.data.holidays.repos.usecases

import `in`.porter.cfms.data.holidays.FranchisesTable
import `in`.porter.cfms.data.holidays.HolidayQueries
import `in`.porter.cfms.data.holidays.HolidayTable
import `in`.porter.cfms.data.holidays.mappers.HolidayMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidayMapper
import `in`.porter.cfms.data.holidays.mappers.ListHolidaysFranchiseRowMapper
import `in`.porter.cfms.data.holidays.mappers.UpdateHolidayMapper
import `in`.porter.cfms.data.holidays.repos.PsqlHolidayRepo
import `in`.porter.cfms.data.holidays.repos.factories.PsqlHolidayRepoFactory
import `in`.porter.cfms.domain.holidays.entities.Holiday
import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.domain.holidays.entities.UpdateHoliday
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
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
    private lateinit var listHolidayMapper: ListHolidayMapper
    private lateinit var listHolidaysFranchiseMapper : ListHolidaysFranchiseRowMapper

    @BeforeEach
    fun setup() {
        holidayQueries = mockk()
        holidayMapper = mockk()
        updateHolidayMapper = mockk()
        listHolidayMapper = mockk()
        listHolidaysFranchiseMapper = mockk()
        holidayRepo = PsqlHolidayRepo(holidayQueries, holidayMapper, updateHolidayMapper, listHolidayMapper, listHolidaysFranchiseMapper)

        // Mocking toRecord and toDomain with valid responses
        coEvery { holidayMapper.toRecord(any()) } returns mockk()
        coEvery { holidayMapper.toDomain(any()) } returns mockk()
        coEvery { updateHolidayMapper.toRecord(any()) } returns mockk()
        coEvery { updateHolidayMapper.toDomain(any()) } returns mockk()
        coEvery { listHolidayMapper.toRecord(any()) } returns mockk()
        coEvery { listHolidayMapper.toDomain(any(), any()) } returns mockk()
        coEvery { listHolidaysFranchiseMapper.toRecord(any()) } returns mockk()
    }

    @Test
    fun `should get holiday by franchise ID and date`() = runBlocking {
        val holidayId = 1
        val franchiseId = "123"
        val startDate = LocalDate.now()
        val endDate = LocalDate.now().plusDays(1)
        val holiday = PsqlHolidayRepoFactory.buildUpdateHolidayEntity(holidayId)

        coEvery { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) } returns mockk()
        coEvery { updateHolidayMapper.toDomain(any()) } returns holiday

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNotNull(result)
        assertEquals(holiday, result)
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) }
        coVerify(exactly = 1) { updateHolidayMapper.toDomain(any()) }
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
        val holidayId = 1
        val franchiseId = "ABC12"
        val startDate = LocalDate.of(2024, 9, 26)
        val endDate = LocalDate.of(2024, 9, 27)
        val holidayRecord = PsqlHolidayRepoFactory.buildUpdateHolidayEntity(holidayId)

        coEvery { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) } returns mockk() // Mocked record from queries
        coEvery { updateHolidayMapper.toDomain(any()) } returns holidayRecord // Mocked domain response

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNotNull(result)
        assertEquals(holidayRecord, result)
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) }
        coVerify(exactly = 1) { updateHolidayMapper.toDomain(any()) }
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

        val defaultHoliday = UpdateHoliday(
            holidayId = 1,
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
        coEvery { updateHolidayMapper.toDomain(any()) } returns defaultHoliday

        val result = holidayRepo.getByIdAndDate(franchiseId, startDate, endDate)

        assertNotNull(result)
        assertEquals(defaultHoliday, result)
        coVerify(exactly = 1) { holidayQueries.getByIdAndDate(franchiseId, startDate, endDate) }
        coVerify(exactly = 1) { updateHolidayMapper.toDomain(any()) }
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

    @Test
    fun `should return ListHoliday when fetching holidays`() = runBlocking {
        val franchiseId = "123"
        val startDate = LocalDate.now()
        val endDate = LocalDate.now().plusDays(1)
        val listHoliday = PsqlHolidayRepoFactory.buildListHoliday()

        // Mock ResultRow to return values for each column that is accessed
        val mockResultRow = mockk<ResultRow>()
        every { mockResultRow[HolidayTable.franchiseId] } returns franchiseId
        every { mockResultRow[HolidayTable.holidayId] } returns listHoliday.holidayId
        every { mockResultRow[HolidayTable.startDate] } returns startDate
        every { mockResultRow[HolidayTable.endDate] } returns endDate
        every { mockResultRow[HolidayTable.holidayName] } returns listHoliday.holidayDetails.name
        every { mockResultRow[HolidayTable.leaveType] } returns listHoliday.holidayDetails.leaveType.toString()
        every { mockResultRow[HolidayTable.backupFranchiseIds] } returns listHoliday.holidayDetails.backupFranchise

        val mockFranchiseRow = mockk<ResultRow>()
        every { mockFranchiseRow[FranchisesTable.franchiseId] } returns listHoliday.franchise.franchiseId
        every { mockFranchiseRow[FranchisesTable.porterHubName] } returns listHoliday.franchise.franchiseName
        every { mockFranchiseRow[FranchisesTable.pocName] } returns listHoliday.franchise.poc.name
        every { mockFranchiseRow[FranchisesTable.primaryNumber] } returns listHoliday.franchise.poc.contact
        every { mockFranchiseRow[FranchisesTable.address] } returns listHoliday.franchise.address.gpsAddress
        every { mockFranchiseRow[FranchisesTable.city] } returns listHoliday.franchise.address.city
        every { mockFranchiseRow[FranchisesTable.state] } returns listHoliday.franchise.address.state

        // Mock the holidayQueries and mappers
        coEvery { holidayQueries.findHolidays(any(), any(), any(), any(), any(), any()) } returns listOf(mockResultRow)
        coEvery { listHolidayMapper.toRecord(mockResultRow) } returns listHoliday
        coEvery { holidayQueries.findFranchiseById(any()) } returns mockFranchiseRow
        coEvery { listHolidaysFranchiseMapper.toRecord(mockFranchiseRow) } returns listHoliday.franchise

        // Run the method being tested
        val result = holidayRepo.findHolidays(franchiseId, LeaveType.Normal, startDate, endDate, 1, 10)

        // Assertions
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(listHoliday, result[0])
        coVerify { holidayQueries.findHolidays(any(), any(), any(), any(), any(), any()) }
        coVerify { listHolidayMapper.toRecord(mockResultRow) }
        coVerify { holidayQueries.findFranchiseById(any()) }
        coVerify { listHolidaysFranchiseMapper.toRecord(mockFranchiseRow) }
    }


    @Test
    fun `should return empty list if no holidays found`() = runBlocking {
        coEvery { holidayQueries.findHolidays(any(), any(), any(), any(), any(), any()) } returns emptyList()

        val result = holidayRepo.findHolidays("123", LeaveType.Normal, LocalDate.now(), LocalDate.now().plusDays(1), 1, 10)

        assertEquals(0, result.size)
    }

    @Test
    fun `should return empty list when franchise ID does not exist`() = runBlocking {
        coEvery { holidayQueries.findHolidays("invalid-id", any(), any(), any(), any(), any()) } returns emptyList()

        val result = holidayRepo.findHolidays("invalid-id", LeaveType.Normal, LocalDate.now(), LocalDate.now().plusDays(1), 1, 10)

        assertEquals(0, result.size)
    }

    @Test
    fun `should return zero when updating a non-existing holiday`() = runBlocking {
        val updateHolidayEntity = PsqlHolidayRepoFactory.buildUpdateHolidayEntity()

        coEvery { updateHolidayMapper.toRecord(any()) } returns mockk()
        coEvery { holidayQueries.updateHoliday(any()) } returns 0

        val result = holidayRepo.update(updateHolidayEntity)
        assertEquals(0, result)
        coVerify(exactly = 1) { holidayQueries.updateHoliday(any()) }
    }

    @Test
    fun `should handle delete for non-existing holiday ID`() = runBlocking {
        val holidayId = 999

        coEvery { holidayQueries.deleteHoliday(holidayId) } returns 0

        val result = holidayRepo.deleteById(holidayId)
        assertEquals(Unit, result) // The deleteById returns Unit, so ensure it's handled gracefully
        coVerify(exactly = 1) { holidayQueries.deleteHoliday(holidayId) }
    }

    @Test
    fun `should return empty list when franchise ID is empty`() = runBlocking {
        coEvery { holidayQueries.findHolidays("", any(), any(), any(), any(), any()) } returns emptyList()

        val result = holidayRepo.findHolidays("", LeaveType.Normal, LocalDate.now(), LocalDate.now().plusDays(1), 1, 10)

        assertEquals(0, result.size)
        coVerify(exactly = 1) { holidayQueries.findHolidays("", any(), any(), any(), any(), any()) }
    }

    @Test
    fun `should return empty list when page number is too high`() = runBlocking {
        coEvery { holidayQueries.findHolidays(any(), any(), any(), any(), 1000, 10) } returns emptyList()

        val result = holidayRepo.findHolidays("123", LeaveType.Normal, LocalDate.now(), LocalDate.now().plusDays(1), 1000, 10)

        assertEquals(0, result.size)
        coVerify(exactly = 1) { holidayQueries.findHolidays(any(), any(), any(), any(), 1000, 10) }
    }

    @Test
    fun `should return holidays when start date is null`() = runBlocking {
        // Arrange: Mock ResultRow with required fields for the holiday
        val mockHolidayResultRow = mockk<ResultRow>()
        every { mockHolidayResultRow[HolidayTable.franchiseId] } returns "123"
        every { mockHolidayResultRow[HolidayTable.holidayId] } returns 1
        every { mockHolidayResultRow[HolidayTable.startDate] } returns LocalDate.now().minusDays(5)
        every { mockHolidayResultRow[HolidayTable.endDate] } returns LocalDate.now().plusDays(5)
        every { mockHolidayResultRow[HolidayTable.holidayName] } returns "Holiday Name"
        every { mockHolidayResultRow[HolidayTable.leaveType] } returns "Normal"
        every { mockHolidayResultRow[HolidayTable.backupFranchiseIds] } returns "456,789"

        // Mock ResultRow for franchise details
        val mockFranchiseResultRow = mockk<ResultRow>()
        every { mockFranchiseResultRow[FranchisesTable.franchiseId] } returns "123"
        every { mockFranchiseResultRow[FranchisesTable.porterHubName] } returns "Porter Hub"
        every { mockFranchiseResultRow[FranchisesTable.pocName] } returns "John Doe"
        every { mockFranchiseResultRow[FranchisesTable.primaryNumber] } returns "9876543210"
        every { mockFranchiseResultRow[FranchisesTable.address] } returns "123 Main St"
        every { mockFranchiseResultRow[FranchisesTable.city] } returns "City Name"
        every { mockFranchiseResultRow[FranchisesTable.state] } returns "State Name"

        // Mocking holidayQueries to return the mocked data
        coEvery { holidayQueries.findHolidays(any(), any(), null, any(), any(), any()) } returns listOf(mockHolidayResultRow)
        coEvery { holidayQueries.findFranchiseById("123") } returns mockFranchiseResultRow

        // Mock the mappers to map the mocked ResultRow to domain entities
        coEvery { listHolidayMapper.toRecord(mockHolidayResultRow) } returns PsqlHolidayRepoFactory.buildListHoliday()
        coEvery { listHolidaysFranchiseMapper.toRecord(mockFranchiseResultRow) } returns PsqlHolidayRepoFactory.buildListHoliday().franchise

        // Act: Execute the function under test
        val result = holidayRepo.findHolidays("123", LeaveType.Normal, null, LocalDate.now().plusDays(1), 1, 10)

        // Assert: Verify the results and interactions
        assertNotNull(result)
        assertEquals(1, result.size)
        coVerify { holidayQueries.findHolidays(any(), any(), null, any(), any(), any()) }
        coVerify { holidayQueries.findFranchiseById("123") }
    }

    @Test
    fun `should return holidays when end date is null`() = runBlocking {
        // Arrange: Mock ResultRow with required fields for the holiday
        val mockHolidayResultRow = mockk<ResultRow>()
        every { mockHolidayResultRow[HolidayTable.franchiseId] } returns "123"
        every { mockHolidayResultRow[HolidayTable.holidayId] } returns 1
        every { mockHolidayResultRow[HolidayTable.startDate] } returns LocalDate.now().minusDays(5)
        every { mockHolidayResultRow[HolidayTable.endDate] } returns LocalDate.now().plusDays(5) // End date is null in this case
        every { mockHolidayResultRow[HolidayTable.holidayName] } returns "Holiday Name"
        every { mockHolidayResultRow[HolidayTable.leaveType] } returns "Normal"
        every { mockHolidayResultRow[HolidayTable.backupFranchiseIds] } returns "456,789"

        // Mock ResultRow for franchise details
        val mockFranchiseResultRow = mockk<ResultRow>()
        every { mockFranchiseResultRow[FranchisesTable.franchiseId] } returns "123"
        every { mockFranchiseResultRow[FranchisesTable.porterHubName] } returns "Porter Hub"
        every { mockFranchiseResultRow[FranchisesTable.pocName] } returns "John Doe"
        every { mockFranchiseResultRow[FranchisesTable.primaryNumber] } returns "9876543210"
        every { mockFranchiseResultRow[FranchisesTable.address] } returns "123 Main St"
        every { mockFranchiseResultRow[FranchisesTable.city] } returns "City Name"
        every { mockFranchiseResultRow[FranchisesTable.state] } returns "State Name"

        // Mocking holidayQueries to return the mocked data
        coEvery { holidayQueries.findHolidays(any(), any(), any(), null, any(), any()) } returns listOf(mockHolidayResultRow)
        coEvery { holidayQueries.findFranchiseById("123") } returns mockFranchiseResultRow

        // Mock the mappers to map the mocked ResultRow to domain entities
        coEvery { listHolidayMapper.toRecord(mockHolidayResultRow) } returns PsqlHolidayRepoFactory.buildListHoliday()
        coEvery { listHolidaysFranchiseMapper.toRecord(mockFranchiseResultRow) } returns PsqlHolidayRepoFactory.buildListHoliday().franchise

        // Act: Execute the function under test
        val result = holidayRepo.findHolidays("123", LeaveType.Normal, LocalDate.now(), null, 1, 10)

        // Assert: Verify the results and interactions
        assertNotNull(result)
        assertEquals(1, result.size)
        coVerify { holidayQueries.findHolidays(any(), any(), any(), null, any(), any()) }
        coVerify { holidayQueries.findFranchiseById("123") }
    }
}
