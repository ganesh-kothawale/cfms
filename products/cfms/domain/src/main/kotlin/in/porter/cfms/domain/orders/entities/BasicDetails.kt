package `in`.porter.cfms.domain.orders.entities


data class BasicDetails(
    val associationDetails: AssociationDetails,
    val orderNumber: String,
    val awbNumber: String?,  // nullable
    val accountId: Int?,  // nullable
    val accountCode: String?,  // nullable
    val courierTransportDetails: CourierTransportDetails,
    val orderStatus: List<String>
)