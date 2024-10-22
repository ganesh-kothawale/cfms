package `in`.porter.cfms.data.pickuptasks.records

data class HlpWithOrdersRecord(
    val taskId : String,
    val hlpOrderId: String,
    val riderName: String,
    val riderNumber: String,
    val vehicleType: String,
    val pickupOrders: List<PickupOrderRecord>
)
