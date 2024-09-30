package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.SenderDetails
import `in`.porter.cfms.domain.orders.entities.SenderDetails as DomainSenderDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class SenderDetailsMapper @Inject constructor(
    private val personalInfoMapper: SenderPersonalInfoMapper,
    private val addressMapper: SenderAddressMapper,
    private val locationMapper: SenderLocationMapper
) {
    fun fromResultRow(row: ResultRow): SenderDetails {
        return SenderDetails(
            personalInfo = personalInfoMapper.fromResultRow(row),
            address = addressMapper.fromResultRow(row),
            location = locationMapper.fromResultRow(row)
        )
    }

    fun toDomain(entity: SenderDetails): DomainSenderDetails {
        return DomainSenderDetails(
            personalInfo = personalInfoMapper.toDomain(entity.personalInfo),
            address = addressMapper.toDomain(entity.address),
            location = locationMapper.toDomain(entity.location)
        )
    }
}