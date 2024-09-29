package `in`.porter.cfms.domain.orders.entities

data class AddressDetails(
    val senderDetails: SenderDetails,
    val receiverDetails: ReceiverDetails
)

