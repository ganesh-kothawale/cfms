package `in`.porter.cfms.data.courierPartners

import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRowMapper
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerRecord
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import javax.inject.Inject

class CourierPartnerQueries
@Inject
constructor(
  override val db: Database,
  override val dispatcher: CoroutineDispatcher,
  private val rowMapper: CourierPartnerRowMapper,
) : ExposedRepo {

    suspend fun getById(id: Int): CourierPartnerRecord? = transact {
        CourierPartnersTable.select { CourierPartnersTable.id eq id }
            .map { rowMapper.toRecord(it) }
            .firstOrNull()
    }

    suspend fun getByIds(ids: List<Int>): List<CourierPartnerRecord> = transact {
        CourierPartnersTable.select { CourierPartnersTable.id inList ids }
            .map { rowMapper.toRecord(it) }
    }
}
