package `in`.porter.cfms.data.cpConnections.mappers

import `in`.porter.cfms.data.cpConnections.CpConnectionTable
import `in`.porter.cfms.data.cpConnections.records.CpConnectionRecord
import `in`.porter.cfms.data.cpConnections.records.CpConnectionRecordData
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class CPConnectionRowMapper
@Inject
constructor() {

    fun toRecord(row: ResultRow) = CpConnectionRecord(
        id = row[CpConnectionTable.id].value,
        data = toRecordData(row),
        createdAt = row[CpConnectionTable.createdAt],
        updatedAt = row[CpConnectionTable.updatedAt],
    )

    private fun toRecordData(row: ResultRow) = CpConnectionRecordData(
        cpId = row[CpConnectionTable.cpId],
        franchiseId = row[CpConnectionTable.franchiseId],
        manifestImageUrl = row[CpConnectionTable.manifestImageUrl],
    )
}
