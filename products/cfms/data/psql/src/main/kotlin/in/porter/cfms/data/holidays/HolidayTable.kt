package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date

object HolidayTable : IntIdTable("holidays") {

    val franchiseId = varchar("franchise_id", 64)
    val startDate = date("start_date")
    val endDate = date("end_date")
    val holidayName = varchar("holiday_name", 128).nullable()
    val leaveType = varchar("leave_type", 16)
    val backupFranchiseIds = varchar("backup_franchise_ids", 128).nullable()
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")

    init {
        uniqueIndex(franchiseId, startDate, endDate)
    }
}