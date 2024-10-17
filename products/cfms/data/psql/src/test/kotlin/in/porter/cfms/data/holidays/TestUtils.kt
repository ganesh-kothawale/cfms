package `in`.porter.cfms.data.holidays

import `in`.porter.cfms.domain.holidays.entities.LeaveType
import `in`.porter.cfms.data.franchise.FranchisesTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.LocalDate


fun insertFranchiseRecord(franchiseId: String) {
    transaction {
        FranchisesTable.insert {
            it[this.franchiseId] = franchiseId
            it[address] = "123 Main St"
            it[city] = "City A"
            it[state] = "State A"
            it[pincode] = "12345"
            it[latitude] = 0.0.toBigDecimal()
            it[longitude] = 0.0.toBigDecimal()
            it[pocName] = "POC A"
            it[primaryNumber] = "0000000000"
            it[email] = "poc@example.com"
            it[status] = "Active"
            it[porterHubName] = "Hub A"
            it[franchiseGst] = "GST1"
            it[franchisePan] = "PAN1"
            it[franchiseCanceledCheque] = "Cheque1"
            it[daysOfOperation] = "Mon-Fri"
            it[startTime] = "09:00"
            it[endTime] = "17:00"
            it[cutOffTime] = "12:00"
            it[hlpEnabled] = true
            it[radiusCoverage] = 10.00.toBigDecimal()
            it[showCrNumber] = false
            it[createdAt] = Instant.now()
            it[updatedAt] = Instant.now()
            it[kamUser] = "KAM1"
            it[teamId] = 1
        }
    }
}

fun insertHolidayRecord(franchiseId: String, startDate: LocalDate, endDate: LocalDate, holidayName: String, leaveType: LeaveType) {
    transaction {
        HolidayTable.insert {
            it[this.franchiseId] = franchiseId
            it[this.startDate] = startDate
            it[this.endDate] = endDate
            it[this.holidayName] = holidayName
            it[this.leaveType] = leaveType.toString()
            it[backupFranchiseIds] = null
            it[createdAt] = Instant.now()
            it[updatedAt] = Instant.now()
        }
    }
}
