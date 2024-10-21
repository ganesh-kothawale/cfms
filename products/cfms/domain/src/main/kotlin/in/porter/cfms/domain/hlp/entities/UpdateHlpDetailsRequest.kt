package `in`.porter.cfms.domain.hlp.entities

data class UpdateHlpDetailsRequest(
    val hlpOrderId: String,
    val hlpOrderStatus: String?,
    val otp: String?,
    val riderName: String?,
    val riderNumber: String?,
    val vehicleType: String?,
)
