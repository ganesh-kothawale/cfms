package `in`.porter.cfms.api.models.pickupTasks

data class PickupTaskHlpResponse(
    val hlpOrderId: String,
    val riderName: String?,
    val riderNumber: String?,
    val vehicleType: String?,
    val pickupOrders: List<PickupTaskOrderResponse>
)
