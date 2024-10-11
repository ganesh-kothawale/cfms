package `in`.porter.cfms.data.holidays.records.factories

import `in`.porter.cfms.data.holidays.records.ListHolidayRecord
import java.time.LocalDate

object ListHolidayRecordFactory {
    fun createDefault(): ListHolidayRecord {
        return ListHolidayRecord(
            holidayId = 1,
            franchiseId = "franchise-123",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 1, 10),
            holidayName = "New Year Holiday",
            leaveType = "Normal",
            backupFranchiseIds = "backup-123"
        )
    }

    fun createCustom(
        holidayId: Int = 1,
        franchiseId: String = "franchise-123",
        startDate: LocalDate = LocalDate.of(2024, 1, 1),
        endDate: LocalDate = LocalDate.of(2024, 1, 10),
        holidayName: String? = "New Year Holiday",
        leaveType: String? = "Normal",
        backupFranchiseIds: String? = "backup-123"
    ): ListHolidayRecord {
        return ListHolidayRecord(
            holidayId = holidayId,
            franchiseId = franchiseId,
            startDate = startDate,
            endDate = endDate,
            holidayName = holidayName,
            leaveType = leaveType,
            backupFranchiseIds = backupFranchiseIds
        )
    }
}
