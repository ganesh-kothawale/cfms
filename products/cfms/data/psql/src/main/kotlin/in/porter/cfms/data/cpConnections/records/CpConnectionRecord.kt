package `in`.porter.cfms.data.cpConnections.records

import java.time.Instant

data class CpConnectionRecord(
    val id: Int,
    val data: CpConnectionRecordData,
    val createdAt: Instant,
    val updatedAt: Instant
)
