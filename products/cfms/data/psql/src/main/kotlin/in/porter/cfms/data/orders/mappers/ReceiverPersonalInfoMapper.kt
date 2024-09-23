package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.PersonalInfo
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class ReceiverPersonalInfoMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): PersonalInfo {
        return PersonalInfo(
            name = row[OrdersTable.receiverName],
            mobileNumber = row[OrdersTable.receiverMobile]
        )
    }
}