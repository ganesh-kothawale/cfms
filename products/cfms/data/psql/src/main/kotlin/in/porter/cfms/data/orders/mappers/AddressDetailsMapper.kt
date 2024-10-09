package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.AddressDetails
import `in`.porter.cfms.domain.orders.entities.AddressDetails as DomainAddressDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class AddressDetailsMapper @Inject constructor(
    private val senderDetailsMapper: SenderDetailsMapper,
    private val receiverDetailsMapper: ReceiverDetailsMapper
) {
    fun fromResultRow(row: ResultRow): AddressDetails {
        return AddressDetails(
            senderDetails = senderDetailsMapper.fromResultRow(row),
            receiverDetails = receiverDetailsMapper.fromResultRow(row)
        )
    }

    fun toDomain(entity: AddressDetails): DomainAddressDetails {
        return DomainAddressDetails(
            senderDetails = senderDetailsMapper.toDomain(entity.senderDetails),
            receiverDetails = receiverDetailsMapper.toDomain(entity.receiverDetails)
        )
    }
}