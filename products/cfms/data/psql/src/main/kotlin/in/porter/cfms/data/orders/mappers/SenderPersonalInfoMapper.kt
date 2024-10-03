package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.PersonalInfo
import `in`.porter.cfms.domain.orders.entities.PersonalInfo as DomainPersonalInfo
import `in`.porter.cfms.data.orders.repos.OrdersTable
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class SenderPersonalInfoMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): PersonalInfo {
        return PersonalInfo(
            name = row[OrdersTable.senderName],
            mobileNumber = row[OrdersTable.senderMobile]
        )
    }

    fun toDomain(entity: PersonalInfo): DomainPersonalInfo {
        return DomainPersonalInfo(
            name = entity.name,
            mobileNumber = entity.mobileNumber
        )
    }
}