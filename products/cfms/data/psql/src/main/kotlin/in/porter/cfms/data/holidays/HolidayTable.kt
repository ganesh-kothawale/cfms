package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object HolidayTable : Table("holidays") {
    val id = integer("id").autoIncrement()
    val holidayId = varchar("holiday_id",10)
    val franchiseId = varchar("franchise_id", 64).references(FranchisesTable.franchiseId)
    val startDate = date("start_date")
    val endDate = date("end_date")
    val holidayName = varchar("holiday_name", 128).nullable()
    val leaveType = varchar("leave_type", 16)
    val backupFranchiseIds = varchar("backup_franchise_ids", 128).nullable()
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(franchiseId, startDate, endDate)
    }
}
