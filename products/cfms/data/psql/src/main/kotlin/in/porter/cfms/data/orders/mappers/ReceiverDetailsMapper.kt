package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.ReceiverDetails
import `in`.porter.cfms.domain.orders.entities.ReceiverDetails as DomainReceiverDetails
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

    fun toDomain(entity: ReceiverDetails): DomainReceiverDetails {
        return DomainReceiverDetails(
            personalInfo = personalInfoMapper.toDomain(entity.personalInfo),
            address = addressMapper.toDomain(entity.address)
        )
    }
}