package `in`.porter.cfms.data.courierPartners.records

import java.time.Instant

data class CourierTableData (
    val id: Int,
    val createdAt: Instant,
    val name: String?
)