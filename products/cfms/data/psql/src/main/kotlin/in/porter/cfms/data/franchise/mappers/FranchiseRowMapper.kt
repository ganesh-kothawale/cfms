package `in`.porter.cfms.data.franchise.mappers

import `in`.porter.cfms.data.franchise.FranchisesTable
import `in`.porter.cfms.data.franchise.records.FranchiseRecord
import `in`.porter.cfms.data.franchise.records.FranchiseRecordData
import `in`.porter.kotlinutils.geos.entities.LatLng
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class FranchiseRowMapper
@Inject
constructor() {

    // Map a ResultRow to FranchiseRecord
    fun toRecord(resultRow: ResultRow) = FranchiseRecord(
        franchiseId = resultRow[FranchisesTable.franchiseId],
        createdAt = resultRow[FranchisesTable.createdAt],
        updatedAt = resultRow[FranchisesTable.updatedAt],
        data= toData(resultRow)
    )

    private fun toData(resultRow: ResultRow) = FranchiseRecordData(
        address = resultRow[FranchisesTable.address],
        city = resultRow[FranchisesTable.city],
        state = resultRow[FranchisesTable.state],
        pincode = resultRow[FranchisesTable.pincode],
        latitude = resultRow[FranchisesTable.latitude],
        longitude = resultRow[FranchisesTable.longitude],
        pocName = resultRow[FranchisesTable.pocName],
        primaryNumber = resultRow[FranchisesTable.primaryNumber],
        email = resultRow[FranchisesTable.email],
        status = resultRow[FranchisesTable.status],
        porterHubName = resultRow[FranchisesTable.porterHubName],
        franchiseGst = resultRow[FranchisesTable.franchiseGst],
        franchisePan = resultRow[FranchisesTable.franchisePan],
        franchiseCanceledCheque = resultRow[FranchisesTable.franchiseCanceledCheque],
        daysOfOperation = resultRow[FranchisesTable.daysOfOperation],
        startTime = resultRow[FranchisesTable.startTime],
        endTime = resultRow[FranchisesTable.endTime],
        cutOffTime = resultRow[FranchisesTable.cutOffTime],
        hlpEnabled = resultRow[FranchisesTable.hlpEnabled],
        radiusCoverage = resultRow[FranchisesTable.radiusCoverage],
        teamId = resultRow[FranchisesTable.teamId],
        kamUser = resultRow[FranchisesTable.kamUser],
        showCrNumber = resultRow[FranchisesTable.showCrNumber],
        franchiseId =resultRow[FranchisesTable.franchiseId]
    )
}
