//package `in`.porter.cfms.api.service.holidays.usecases
//
//import `in`.porter.cfms.api.service.holidays.factories.CreateHolidaysRequestTestFactory
//import `in`.porter.cfms.api.models.exceptions.CfmsException
//import `in`.porter.cfms.api.service.holidays.mappers.CreateHolidaysRequestMapper
//import `in`.porter.cfms.api.service.holidays.usecases.CreateHolidaysService
//import `in`.porter.cfms.domain.holidays.entities.Holiday
//import `in`.porter.cfms.domain.holidays.usecases.CreateHoliday
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.junit.jupiter.api.assertThrows
//import java.time.LocalDate
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class CreateHolidaysServiceTest {
//
//    private lateinit var createHolidaysService: CreateHolidaysService
//    private lateinit var createHoliday: CreateHoliday
//    private lateinit var mapper: CreateHolidaysRequestMapper
//
//    @BeforeEach
//    fun setup() {
//        createHoliday = mockk()
//        mapper = CreateHolidaysRequestMapper()  // Instantiate the mapper
//        createHolidaysService = CreateHolidaysService(
//            createHoliday,
//            mapper
//        )
//    }
//
//    @Test
//    fun `should throw exception when start date is before today`() = runBlocking {
//        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(
//            startDate = LocalDate.now().minusDays(1),
//            endDate = LocalDate.now()
//        )
//
//        val exception = assertThrows<CfmsException> {
//            createHolidaysService.invoke(request)
//        }
//
//        assertEquals("Start date cannot be before today's date.", exception.message)
//        coVerify(exactly = 0) { createHoliday.createHoliday(any()) }
//    }
//
//    @Test
//    fun `should throw exception when end date is before start date`() = runBlocking {
//        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(
//            startDate = LocalDate.now().plusDays(2),
//            endDate = LocalDate.now().plusDays(1)
//        )
//
//        val exception = assertThrows<CfmsException> {
//            createHolidaysService.invoke(request)
//        }
//
//        assertEquals("End date cannot be before start date.", exception.message)
//        coVerify(exactly = 0) { createHoliday.createHoliday(any()) }
//    }
//
//    @Test
//    fun `should throw exception when holiday already exists`() = runBlocking {
//        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest()
//
//        coEvery { createHoliday.createHoliday(any<Holiday>()) } throws CfmsException("Holiday already exists")
//
//        val exception = assertThrows<CfmsException> {
//            createHolidaysService.invoke(request)
//        }
//
//        assertEquals("Failed to store holiday in DB: Holiday already exists", exception.message)
//        coVerify(exactly = 1) { createHoliday.createHoliday(any<Holiday>()) }
//    }
//
//    @Test
//    fun `should throw exception when storing holiday fails`() = runBlocking {
//        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest()
//
//        coEvery { createHoliday.createHoliday(any<Holiday>()) } throws Exception("DB error")
//
//        val exception = assertThrows<CfmsException> {
//            createHolidaysService.invoke(request)
//        }
//
//        assertEquals("Failed to store holiday in DB: DB error", exception.message)
//        coVerify(exactly = 1) { createHoliday.createHoliday(any<Holiday>()) }
//    }
//
//    @Test
//    fun `should successfully create a holiday with Normal leave type`() = runBlocking {
//        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(
//            leaveType = `in`.porter.cfms.api.models.holidays.LeaveType.Normal
//        )
//
//        coEvery { createHoliday.createHoliday(any<Holiday>()) } returns 1
//
//        val holidayId = createHolidaysService.invoke(request)
//
//        assertEquals(1, holidayId)
//
//        coVerify(exactly = 1) { createHoliday.createHoliday(any<Holiday>()) }
//    }
//
//    @Test
//    fun `should successfully create a holiday with Emergency leave type`() = runBlocking {
//        val request = CreateHolidaysRequestTestFactory.buildCreateHolidaysRequest(
//            leaveType = `in`.porter.cfms.api.models.holidays.LeaveType.Emergency
//        )
//
//        coEvery { createHoliday.createHoliday(any<Holiday>()) } returns 1
//
//        val holidayId = createHolidaysService.invoke(request)
//
//        assertEquals(1, holidayId)
//
//        coVerify(exactly = 1) { createHoliday.createHoliday(any<Holiday>()) }
//    }
//}
