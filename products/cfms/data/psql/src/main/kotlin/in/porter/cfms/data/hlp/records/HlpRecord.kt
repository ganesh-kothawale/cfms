package `in`.porter.cfms.data.hlp.records

import java.time.Instant

data class HlpRecord(
    val id: Int,
    val data: HlpRecordData,
    val createdAt: Instant,
    val updatedAt: Instant,
)
