package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.ReceiverDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class ReceiverDetailsMapper @Inject constructor(
    private val personalInfoMapper: ReceiverPersonalInfoMapper,
    private val addressMapper: ReceiverAddressMapper
) {
    fun fromResultRow(row: ResultRow): ReceiverDetails {
        return ReceiverDetails(
            personalInfo = personalInfoMapper.fromResultRow(row),
            address = addressMapper.fromResultRow(row)
        )
    }
}