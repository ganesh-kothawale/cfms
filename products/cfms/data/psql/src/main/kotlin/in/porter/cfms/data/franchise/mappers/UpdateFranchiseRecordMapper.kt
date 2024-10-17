package `in`.porter.cfms.data.franchise.mappers

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.franchise.records.UpdateFranchiseRecord
import `in`.porter.cfms.data.franchise.records.UpdateFranchiseDataRecord
import `in`.porter.cfms.domain.franchise.FranchiseStatus
import `in`.porter.cfms.domain.franchise.entities.UpdateFranchise
import javax.inject.Inject

class UpdateFranchiseRecordMapper
@Inject
constructor() {
    fun toFranchiseRecord(updateFranchise: UpdateFranchise): UpdateFranchiseRecord {
        return try {
            UpdateFranchiseRecord(
                franchiseId = updateFranchise.franchiseId,
                createdAt = updateFranchise.createdAt,
                updatedAt = updateFranchise.updatedAt,
                data = UpdateFranchiseDataRecord(
                    pocName = updateFranchise.pocName,
                    primaryNumber = updateFranchise.primaryNumber,
                    email = updateFranchise.email,
                    address = updateFranchise.address,
                    latitude = updateFranchise.latitude,
                    longitude = updateFranchise.longitude,
                    city = updateFranchise.city,
                    state = updateFranchise.state,
                    pincode = updateFranchise.pincode,
                    porterHubName = updateFranchise.porterHubName,
                    franchiseGst = updateFranchise.franchiseGst,
                    franchisePan = updateFranchise.franchisePan,
                    franchiseCanceledCheque = updateFranchise.franchiseCanceledCheque,
                    status = updateFranchise.status.name,
                    teamId = updateFranchise.teamId,
                    daysOfOperation = updateFranchise.daysOfOperation,
                    startTime = updateFranchise.startTime,
                    endTime = updateFranchise.endTime,
                    cutOffTime = updateFranchise.cutOffTime,
                    kamUser = updateFranchise.kamUser,
                    hlpEnabled = updateFranchise.hlpEnabled,
                    radiusCoverage = updateFranchise.radiusCoverage,
                    showCrNumber = updateFranchise.showCrNumber,
                    isActive = updateFranchise.isActive,
                    daysOfTheWeek = updateFranchise.daysOfTheWeek,
                    courierPartners = updateFranchise.courierPartners
                )
            )
        } catch (e: CfmsException) {
            throw CfmsException("Failed to map Franchise to Record")
        }
    }


    fun toDomain(franchiseRecord: UpdateFranchiseRecord): UpdateFranchise {
        return UpdateFranchise(
            franchiseId = franchiseRecord.franchiseId,
            createdAt = franchiseRecord.createdAt,
            updatedAt = franchiseRecord.updatedAt,
            pocName = franchiseRecord.data.pocName,
            primaryNumber = franchiseRecord.data.primaryNumber,
            email = franchiseRecord.data.email,
            address = franchiseRecord.data.address,
            latitude = franchiseRecord.data.latitude,
            longitude = franchiseRecord.data.longitude,
            city = franchiseRecord.data.city,
            state = franchiseRecord.data.state,
            pincode = franchiseRecord.data.pincode,
            porterHubName = franchiseRecord.data.porterHubName,
            franchiseGst = franchiseRecord.data.franchiseGst,
            franchisePan = franchiseRecord.data.franchisePan,
            franchiseCanceledCheque = franchiseRecord.data.franchiseCanceledCheque,
            status = FranchiseStatus.valueOf(franchiseRecord.data.status),
            teamId = franchiseRecord.data.teamId,
            daysOfOperation = franchiseRecord.data.daysOfOperation,
            startTime = franchiseRecord.data.startTime,
            endTime = franchiseRecord.data.endTime,
            cutOffTime = franchiseRecord.data.cutOffTime,
            kamUser = franchiseRecord.data.kamUser,
            hlpEnabled = franchiseRecord.data.hlpEnabled,
            radiusCoverage = franchiseRecord.data.radiusCoverage,
            showCrNumber = franchiseRecord.data.showCrNumber,
            isActive = franchiseRecord.data.isActive,
            daysOfTheWeek = franchiseRecord.data.daysOfTheWeek,
            courierPartners = franchiseRecord.data.courierPartners
        )
    }
}
