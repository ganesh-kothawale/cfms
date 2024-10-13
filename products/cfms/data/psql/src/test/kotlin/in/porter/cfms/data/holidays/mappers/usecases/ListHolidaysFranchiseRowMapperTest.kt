package `in`.porter.cfms.data.holidays.mappers.usecases

import `in`.porter.cfms.data.holidays.mappers.ListHolidaysFranchiseRowMapper
import `in`.porter.cfms.data.holidays.mappers.factories.ListHolidaysFranchiseRowMapperFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListHolidaysFranchiseRowMapperTest {

    private lateinit var listHolidaysFranchiseRowMapper: ListHolidaysFranchiseRowMapper

    @BeforeEach
    fun setup() {
        listHolidaysFranchiseRowMapper = ListHolidaysFranchiseRowMapper()
    }

    @Test
    fun `should map ResultRow to ListHolidaysFranchise`() {
        val resultRow = ListHolidaysFranchiseRowMapperFactory.createResultRow()

        val result = listHolidaysFranchiseRowMapper.toRecord(resultRow)

        assertEquals("franchise-123", result.franchiseId)
        assertEquals("Franchise A", result.franchiseName)
        assertEquals("John Doe", result.poc.name)
        assertEquals("1234567890", result.poc.contact)
        assertEquals("123 Main St", result.address.gpsAddress)
        assertEquals("Sample City", result.address.city)
        assertEquals("Sample State", result.address.state)
    }
}
