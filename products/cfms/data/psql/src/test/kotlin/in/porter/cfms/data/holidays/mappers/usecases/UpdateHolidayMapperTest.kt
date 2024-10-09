package `in`.porter.cfms.data.holidays.mappers.usecases

import `in`.porter.cfms.data.holidays.mappers.UpdateHolidayMapper
import `in`.porter.cfms.data.holidays.mappers.factories.UpdateHolidayMapperFactory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateHolidayMapperTest {

    private lateinit var updateHolidayMapper: UpdateHolidayMapper

    @BeforeEach
    fun setup() {
        updateHolidayMapper = UpdateHolidayMapper()
    }

    @Test
    fun `should map UpdateHolidayEntity to UpdateHolidayRecord`() {
        val updateHolidayEntity = UpdateHolidayMapperFactory.buildUpdateHolidayEntity()

        val record = updateHolidayMapper.toRecord(updateHolidayEntity)

        assertEquals(updateHolidayEntity.holidayId, record.holidayId)
        assertEquals(updateHolidayEntity.franchiseId, record.franchiseId)
        assertEquals(updateHolidayEntity.startDate, record.startDate)
        assertEquals(updateHolidayEntity.endDate, record.endDate)
        assertEquals(updateHolidayEntity.holidayName, record.holidayName)
        assertEquals(updateHolidayEntity.leaveType, record.leaveType)
        assertEquals(updateHolidayEntity.createdAt, record.createdAt)
        assertEquals(updateHolidayEntity.updatedAt, record.updatedAt)
    }

    @Test
    fun `should map UpdateHolidayRecord to UpdateHolidayEntity`() {
        val updateHolidayRecord = UpdateHolidayMapperFactory.buildUpdateHolidayRecord()

        val entity = updateHolidayMapper.toDomain(updateHolidayRecord)

        assertEquals(updateHolidayRecord.holidayId, entity.holidayId)
        assertEquals(updateHolidayRecord.franchiseId, entity.franchiseId)
        assertEquals(updateHolidayRecord.startDate, entity.startDate)
        assertEquals(updateHolidayRecord.endDate, entity.endDate)
        assertEquals(updateHolidayRecord.holidayName, entity.holidayName)
        assertEquals(updateHolidayRecord.leaveType, entity.leaveType)
        assertEquals(updateHolidayRecord.createdAt, entity.createdAt)
        assertEquals(updateHolidayRecord.updatedAt, entity.updatedAt)
    }
}
