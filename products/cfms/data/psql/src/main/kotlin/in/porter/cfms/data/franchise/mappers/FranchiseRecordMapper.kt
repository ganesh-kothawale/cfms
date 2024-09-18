package `in`.porter.cfms.data.franchise.mappers

import `in`.porter.cfms.data.franchise.records.FranchiseRecord
import `in`.porter.cfms.data.franchise.records.FranchiseRecordData
import `in`.porter.cfms.domain.franchise.FranchiseStatus
import `in`.porter.cfms.domain.franchise.entities.Franchise

import javax.inject.Inject

class FranchiseRecordMapper @Inject constructor() {

    fun toRecord(franchise: Franchise): FranchiseRecordData = FranchiseRecordData(
        address = franchise.address,
        city = franchise.city,
        state = franchise.state,
        pincode = franchise.pincode,
        latitude = franchise.latitude,
        longitude = franchise.longitude,
        pocName = franchise.pocName,
        primaryNumber = franchise.primaryNumber,
        email = franchise.email,
        status = franchise.status.name,
        porterHubName = franchise.porterHubName,
        franchiseGst = franchise.franchiseGst,
        franchisePan = franchise.franchisePan,
        franchiseCanceledCheque = franchise.franchiseCanceledCheque,
        daysOfOperation = franchise.daysOfOperation,
        startTime = franchise.startTime,
        endTime = franchise.endTime,
        cutOffTime = franchise.cutOffTime,
        hlpEnabled = franchise.hlpEnabled,
        radiusCoverage = franchise.radiusCoverage,
        showCrNumber = franchise.showCrNumber,
        kamUser = franchise.kamUser,
        teamId = franchise.teamId,
        franchiseId = franchise.franchiseId
    )

    fun fromRecord(record: FranchiseRecord) = Franchise(
        franchiseId = record.franchiseId,
        address = record.data.address,
        city = record.data.city,
        state = record.data.state,
        pincode = record.data.pincode,
        latitude = record.data.latitude,
        longitude = record.data.longitude,
        pocName = record.data.pocName,
        primaryNumber = record.data.primaryNumber,
        email = record.data.email,
        status = FranchiseStatus.valueOf(record.data.status),
        porterHubName = record.data.porterHubName,
        franchiseGst = record.data.franchiseGst,
        franchisePan = record.data.franchisePan,
        franchiseCanceledCheque = record.data.franchiseCanceledCheque,
        daysOfOperation = record.data.daysOfOperation,
        startTime = record.data.startTime,
        endTime = record.data.endTime,
        cutOffTime = record.data.cutOffTime,
        hlpEnabled = record.data.hlpEnabled,
        radiusCoverage = record.data.radiusCoverage,
        showCrNumber = record.data.showCrNumber,
        createdAt = record.createdAt,
        updatedAt = record.updatedAt,
        kamUser = record.data.kamUser,
        teamId = record.data.teamId
    )
}
