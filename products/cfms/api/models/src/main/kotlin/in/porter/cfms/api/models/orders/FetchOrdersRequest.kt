package `in`.porter.cfms.api.models.orders

import java.time.LocalDateTime

data class FetchOrdersRequest(
    val page: Int,
    val size: Int,
    val franchiseId: List<String>? = null,
    val createdDate: LocalDateTime? = null,
    val updatedDate: LocalDateTime? = null,
    val orderStatus: String? = null,
    val orderId: List<String>? = null,
    val awbNumber: List<String>? = null,
    val courierPartnerName: List<String>? = null,
    val senderName: List<String>? = null,
    val senderPhoneNo: List<String>? = null,
    val senderCityName: List<String>? = null,
    val senderPinCode: String? = null,
    val pickupDate: LocalDateTime? = null,
    val isFranchiseUpdated: Boolean? = null,
    val receiverName: List<String>? = null,
    val receiverPhoneNo: List<String>? = null,
    val receiverCityName: List<String>? = null,
    val receiverPinCode: String? = null,
    val hlpOrderId: List<String>? = null,
    val hlpOrderStatus: String? = null,
    val vehicleType: String? = null,
    val riderNumber: String? = null
)
