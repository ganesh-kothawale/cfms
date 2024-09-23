package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.AddressDetails
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
}