package `in`.porter.cfms.api.models.hlp

import java.time.LocalDate

data class FetchHlpRecordsRequest(
    val page: Int,
    val size: Int,
    val createdDate: LocalDate? = null,
    val updatedDate: LocalDate? = null,
    val driverNames: List<String>? = null,
    val driverNumber: String? = null,
    val hlpOrderIds: List<String>? = null,
    val hlpOrderStatus: String? = null,
    val franchiseIds: List<String>? = null,
    val vehicleTypes: List<String>? = null
)
