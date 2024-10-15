package `in`.porter.cfms.data.franchise.mappers

import arrow.data.valid
import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.franchise.records.UpdateFranchiseRecord
import `in`.porter.cfms.data.franchise.records.UpdateFranchiseDataRecord
import `in`.porter.cfms.domain.franchise.FranchiseStatus
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class UpdateFranchiseRowMapper
@Inject
constructor() {

    fun toFranchiseRecord(resultRow: ResultRow): UpdateFranchiseRecord {
        return try {
            UpdateFranchiseRecord(
                franchiseId = resultRow[FranchisesTable.franchiseId],
                createdAt = resultRow[FranchisesTable.createdAt],
                updatedAt = resultRow[FranchisesTable.updatedAt],
                data = UpdateFranchiseDataRecord(
                    pocName = resultRow[FranchisesTable.pocName],
                    primaryNumber = resultRow[FranchisesTable.primaryNumber],
                    email = resultRow[FranchisesTable.email],
                    address = resultRow[FranchisesTable.address],
                    latitude = resultRow[FranchisesTable.latitude],
                    longitude = resultRow[FranchisesTable.longitude],
                    city = resultRow[FranchisesTable.city],
                    state = resultRow[FranchisesTable.state],
                    pincode = resultRow[FranchisesTable.pincode],
                    porterHubName = resultRow[FranchisesTable.porterHubName],
                    franchiseGst = resultRow[FranchisesTable.franchiseGst],
                    franchisePan = resultRow[FranchisesTable.franchisePan],
                    franchiseCanceledCheque = resultRow[FranchisesTable.franchiseCanceledCheque],
                    status = resultRow[FranchisesTable.status], // Map FranchiseStatus enum to its string representation
                    teamId = resultRow[FranchisesTable.teamId],
                    daysOfOperation = resultRow[FranchisesTable.daysOfOperation],
                    startTime = resultRow[FranchisesTable.startTime],
                    endTime = resultRow[FranchisesTable.endTime],
                    cutOffTime = resultRow[FranchisesTable.cutOffTime],
                    kamUser = resultRow[FranchisesTable.kamUser],
                    hlpEnabled = resultRow[FranchisesTable.hlpEnabled],
                    radiusCoverage = resultRow[FranchisesTable.radiusCoverage],
                    showCrNumber = resultRow[FranchisesTable.showCrNumber],
                    isActive = resultRow[FranchisesTable.isActive],
                    daysOfTheWeek = resultRow[FranchisesTable.daysOfTheWeek],
                    courierPartners = resultRow[FranchisesTable.courierPartners].split(",").toList()
                )
            )
        } catch (e: Exception) {
            throw CfmsException("Failed to map ResultRow to UpdateFranchiseRecord")
        }
    }
}
