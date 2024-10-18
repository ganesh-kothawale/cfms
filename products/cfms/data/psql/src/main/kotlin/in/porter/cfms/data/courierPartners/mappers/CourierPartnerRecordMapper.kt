package `in`.porter.cfms.data.courierPartners.mappers

import `in`.porter.cfms.data.courierPartners.records.CourierPartnerRecord
import `in`.porter.cfms.domain.courierPartners.entities.CourierPartner
import javax.inject.Inject

class CourierPartnerRecordMapper
@Inject
constructor() {

    fun fromRecord(record: CourierPartnerRecord) = CourierPartner(
        id = record.id,
        name = record.name,
        createdAt = record.createdAt,
        updatedAt = record.updatedAt,
    )
}
