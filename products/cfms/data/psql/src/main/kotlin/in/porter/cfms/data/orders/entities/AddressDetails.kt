package `in`.porter.cfms.data.orders.entities

data class AddressDetails(
    val senderDetails: SenderDetails,
    val receiverDetails: ReceiverDetails
)