package `in`.porter.cfms.api.models.orders

data class AddressDetails(
    val senderDetails: SenderDetails,
    val receiverDetails: ReceiverDetails
)

