package `in`.porter.cfms.data.orders.entities

data class BasicDetails(
    val orderNumber: String,
    val awbNumber: String?,
    val courierTransportDetails: CourierTransportDetails,
    val orderStatus: String,
    val associationDetails: AssociationDetails,
    val accountId: Int??,
    val accountCode: String?
)