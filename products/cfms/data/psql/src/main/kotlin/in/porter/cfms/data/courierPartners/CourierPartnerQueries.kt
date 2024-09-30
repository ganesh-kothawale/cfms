package `in`.porter.cfms.data.courierPartners

import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.apache.logging.log4j.kotlin.Logging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import javax.inject.Inject


class CourierPartnerQueries
@Inject
constructor(
  override val db: Database,
  override val dispatcher: CoroutineDispatcher,
) : ExposedRepo {

  suspend fun record(req: CourierPartnerRecord): Int = transact {
    // Insert the courier partner and return the generated ID
    CpConnectionTable.insertAndGetId {
      it[franchiseId] = req.franchiseId
      it[createdAt] = req.createdAt
      it[cpId] = req.courierPartnerId
      it[manifestImageUrl] = req.manifestImageUrl
    }.value




  }
}