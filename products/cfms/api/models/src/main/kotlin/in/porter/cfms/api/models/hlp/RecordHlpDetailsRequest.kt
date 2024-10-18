package `in`.porter.cfms.api.models.hlp

data class RecordHlpDetailsRequest(
    val hlpOrderId: String,
    val hlpOrderStatus: String?,
    val otp: String?,
    val riderName: String?,
    val riderNumber: String?,
    val vehicleType: String?,
    val franchiseId: String,
)
