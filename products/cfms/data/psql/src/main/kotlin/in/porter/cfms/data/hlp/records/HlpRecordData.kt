package `in`.porter.cfms.data.hlp.records

data class HlpRecordData(
    val hlpOrderId: String,
    val hlpOrderStatus: String?,
    val otp: String?,
    val riderName: String?,
    val riderNumber: String?,
    val vehicleType: String?,
    val franchiseId: String,
)
