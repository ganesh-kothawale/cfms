package `in`.porter.cfms.data.courierPartners

import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerData
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerTableData
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerDomain
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerRecord
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsRequest
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.apache.logging.log4j.kotlin.Logging
import org.jetbrains.exposed.sql.*
import javax.inject.Inject


class CourierPartnerQueries
@Inject
constructor(
  override val db: Database,
  override val dispatcher: CoroutineDispatcher,
  val mapper : CourierPartnerRecordMapper
) : ExposedRepo {

  suspend fun record(req: CourierPartnerData): Int = transact {
    // Insert the courier partner and return the generated ID
    CpConnectionTable.insertAndGetId {
      it[franchiseId] = req.franchiseId
      it[createdAt] = req.createdAt
      it[cpId] = req.courierPartnerId
      it[manifestImageUrl] = req.manifestImageUrl
    }.value
  }

  suspend fun fetchCp(limit: Int, offset: Int, franchiseId: Int?): List<CourierPartnerTableData> = transact {

      if(franchiseId != null){
        CpConnectionTable.selectAll().andWhere { CpConnectionTable.franchiseId eq franchiseId }.orderBy(CpConnectionTable.createdAt, SortOrder.DESC)
          .limit(limit,offset)
      }
     else {
        CpConnectionTable.selectAll().orderBy(CpConnectionTable.createdAt, SortOrder.DESC).limit(limit, offset)
    }
        .let { mapper.mapOrders(it) }
  }

  suspend fun getCpCount(): Int = transact {
    CpConnectionTable.selectAll().count()
  }
}