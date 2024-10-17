package `in`.porter.cfms.data.courierPartners.records

data class CpConnectionRecordData(
    val courierPartnerId: Int,
    val franchiseId: Int,
    val manifestImageUrl: String?,
    val courierPartnerName: String?,
)
