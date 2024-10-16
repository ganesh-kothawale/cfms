package `in`.porter.cfms.data.franchise.mappers

import `in`.porter.cfms.data.franchise.records.ListFranchisesRecord
import `in`.porter.cfms.domain.franchise.entities.Address
import `in`.porter.cfms.domain.franchise.entities.ListFranchise
import `in`.porter.cfms.domain.franchise.entities.PointOfContact
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ListFranchisesMapper
@Inject
constructor() {
    private val logger = LoggerFactory.getLogger(ListFranchisesMapper::class.java)
    fun toDomain(record: ListFranchisesRecord): ListFranchise {
        logger.info("Mapping record to domain: $record")
        return ListFranchise(
            franchiseId = record.franchiseId,
            address = Address(
                lat = record.latitude,
                lng = record.longitude,
                address = record.address,
                city = record.city,
                state = record.state,
                pincode = record.pincode

            ),
            poc = PointOfContact(
                name = record.pocName,
                mobile = record.pocMobile,
                email = record.pocEmail
            ),
            radiusCovered = record.radiusCovered,
            hlpEnabled = record.hlpEnabled,
            isActive = record.isActive,
            daysOfTheWeek = record.daysOfTheWeek,
            cutOffTime = record.cutOffTime,
            startTime = record.startTime,
            endTime = record.endTime,
            holidays = record.holidays,
            porterHubName = record.porterHubName,
            franchiseGst = record.franchiseGst,
            franchisePan = record.franchisePan,
            franchiseCanceledCheque = record.franchiseCanceledCheque,
            courierPartners = record.courierPartners,
            kamUser = record.kamUser,
            createdAt = record.createdAt,
            updatedAt = record.updatedAt
        )
    }
}
