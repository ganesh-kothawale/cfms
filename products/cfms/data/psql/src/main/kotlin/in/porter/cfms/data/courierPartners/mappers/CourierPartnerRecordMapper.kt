package `in`.porter.cfms.data.courierPartners.mappers

import `in`.porter.cfms.data.courierPartners.CpConnectionTable
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerData
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerTableData
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerDomain
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerRecord
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import javax.inject.Inject


class CourierPartnerRecordMapper
  @Inject
  constructor(){

  fun toRecord(createCourierPartnerRequest: CreateCourierPartnerRequest) = CourierPartnerData(
    courierPartnerId = createCourierPartnerRequest.courierPartnerId,
    franchiseId = createCourierPartnerRequest.franchiseId,
    manifestImageUrl = createCourierPartnerRequest.manifestImageLink,
    createdAt =  Instant.now()
    )

  fun fromResultRow(row: ResultRow): CourierPartnerTableData {
    return CourierPartnerTableData(
      id =  row[CpConnectionTable.id].value,
      createdAt =  row[CpConnectionTable.createdAt],
      courierPartnerId =   row[CpConnectionTable.cpId],
      franchiseId =   row[CpConnectionTable.franchiseId],
      manifestImageUrl =  row[CpConnectionTable.manifestImageUrl]
    )
  }

  fun toDomain(entity : CourierPartnerTableData) : CourierPartnerDomain {
    return CourierPartnerDomain(
      id = entity.id,
      createdAt = entity.createdAt,
      cpId =  entity.courierPartnerId,
      franchiseId = entity.franchiseId,
      manifestImageUrl = entity.manifestImageUrl

    )
  }

  fun mapOrders (query: Query): List<CourierPartnerTableData> {
    return query.map { row: ResultRow -> fromResultRow(row) }
  }
  }

