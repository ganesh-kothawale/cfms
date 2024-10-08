package `in`.porter.cfms.data.franchise

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.dao.id.IntIdTable

object FranchisesTable : IntIdTable() {
        val franchiseId = varchar("franchise_id", 255)
        val address = varchar("address", 255)
        val city = varchar("city", 255)
        val state = varchar("state", 255)
        val pincode = varchar("pincode", 10)
        val latitude = decimal("latitude", 10, 6)
        val longitude = decimal("longitude", 10, 6)
        val pocName = varchar("poc_name", 255)
        val primaryNumber = varchar("primary_number", 20)
        val email = varchar("email", 255)
        val status = varchar("status", 50)
        val porterHubName = varchar("porter_hub_name", 255)
        val franchiseGst = varchar("franchise_gst", 255)
        val franchisePan = varchar("franchise_pan", 255)
        val franchiseCanceledCheque = varchar("franchise_canceled_cheque", 255)
        val daysOfOperation = varchar("days_of_operation", 50)
        val startTime = timestampWithoutTZAsInstant("start_time")
        val endTime = timestampWithoutTZAsInstant("end_time")
        val cutOffTime = timestampWithoutTZAsInstant("cut_off_time")
        val hlpEnabled = bool("hlp_enabled")
        val radiusCoverage = decimal("radius_coverage", 5, 2)
        val showCrNumber = bool("show_cr_number")
        val createdAt = timestampWithoutTZAsInstant("created_at")
        val updatedAt = timestampWithoutTZAsInstant("updated_at")
        val kamUser = varchar("kam_user", 255)
        val teamId = integer("team_id")
}
