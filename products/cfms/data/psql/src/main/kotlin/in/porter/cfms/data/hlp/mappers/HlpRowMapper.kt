package `in`.porter.cfms.data.hlp.mappers

import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.hlp.records.HlpRecord
import `in`.porter.cfms.data.hlp.records.HlpRecordData
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class HlpRowMapper
@Inject
constructor() {

    fun toRecord(row: ResultRow) = HlpRecord(
        id = row[HlpsTable.id].value,
        data = toRecordData(row),
        createdAt = row[HlpsTable.createdAt],
        updatedAt = row[HlpsTable.updatedAt]
    )

    private fun toRecordData(row: ResultRow) = HlpRecordData(
        hlpOrderId = row[HlpsTable.hlpOrderId],
        hlpOrderStatus = row[HlpsTable.hlpOrderStatus],
        otp = row[HlpsTable.otp],
        riderName = row[HlpsTable.riderName],
        riderNumber = row[HlpsTable.riderNumber],
        vehicleType = row[HlpsTable.vehicleType],
        franchiseId = row[HlpsTable.franchiseId],
    )
}
