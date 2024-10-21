package `in`.porter.cfms.data.courierPartners.mappers

import `in`.porter.cfms.data.courierPartners.CourierPartnersTable
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerRecord
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class CourierPartnerRowMapper
@Inject
constructor() {

    fun toRecord(row: ResultRow) = CourierPartnerRecord(
        id = row[CourierPartnersTable.id].value,
        name = row[CourierPartnersTable.name],
        createdAt = row[CourierPartnersTable.createdAt],
        updatedAt = row[CourierPartnersTable.updatedAt],
    )
}
