package `in`.porter.cfms.api.models.orders

data class CreateOrderApiRequest(
    val tableType: String,
    val date: OrderData
)

data class OrderData(
    val franchiseId: String,
    val orderNumber: String,
    val awbNumber: String?,  // nullable
    val courierPartnerName: String,
    val accountId: Int?,  // nullable
    val accountCode: String?,  // nullable
    val modeOfTransport: String,
    val senderName: String,
    val senderMobileNumber: String,
    val senderHouseNumber: String,
    val senderAddressDetails: String,
    val senderCityName: String,
    val senderStateName: String,
    val senderPincode: Int,
    val senderLat: Double,
    val senderLong: Double,
    val receiverName: String,
    val receiverMobileNumber: String,
    val receiverHouseNumber: String,
    val receiverAddressDetails: String,
    val receiverCityName: String,
    val receiverStateName: String,
    val shippingLabelLink: String?,  // nullable
    val pickUpDate: String,
    val materialType: String,
    val materialWeight: Int,
    val length: Double?,  // nullable
    val breadth: Double?,  // nullable
    val height: Double?,  // nullable
    val volumetricWeight: Int?,  // nullable
    val orderStatus: List<String>,
    val zorpTeamId: String?  // nullable
)