package `in`.porter.cfms.data.cpConnections.mappers

import `in`.porter.cfms.data.cpConnections.records.CpConnectionRecord
import `in`.porter.cfms.data.cpConnections.records.CpConnectionRecordData
import `in`.porter.cfms.domain.cpConnections.entities.CPConnection
import `in`.porter.cfms.domain.cpConnections.entities.RecordCPConnectionRequest
import javax.inject.Inject

class CPConnectionRecordMapper
@Inject
constructor() {

  fun toRecordData(request: RecordCPConnectionRequest) = CpConnectionRecordData(
    cpId = request.cpId,
    franchiseId = request.franchiseId,
    manifestImageUrl = request.manifestImageLink,
  )

  fun fromRecord(record: CpConnectionRecord) = CPConnection(
    id = record.id,
    cpId = record.data.cpId,
    franchiseId = record.data.franchiseId,
    manifestImageUrl = record.data.manifestImageUrl,
    createdAt = record.createdAt,
    updatedAt = record.updatedAt
  )
}
