package `in`.porter.cfms.data.franchise.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.franchise.FranchiseQueries
import `in`.porter.cfms.data.franchise.mappers.FranchiseRecordMapper
import `in`.porter.cfms.data.franchise.mappers.UpdateFranchiseRecordMapper
import `in`.porter.cfms.data.franchise.mappers.UpdateFranchiseRowMapper
import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.entities.UpdateFranchise
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.holidays.entities.UpdateHoliday
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class PsqlFranchisesRepo
@Inject constructor(
    private val queries: FranchiseQueries,
    private val mapper: FranchiseRecordMapper,
    private val updateMapper: UpdateFranchiseRecordMapper
) : Traceable, FranchiseRepo {

    override suspend fun create(franchiseRequest: Franchise): Unit =
        trace("create") {
            try {
                mapper.toRecord(franchiseRequest)
                    .let { queries.save(it) }
            } catch (e: CfmsException) {
                throw CfmsException("Failed to create franchise: ${e.message}")
            }
        }

    override suspend fun getByCode(franchiseCode: String): Franchise? =
        trace("getByCode") {
            queries.getByCode(franchiseCode)
                ?.let { mapper.fromRecord(it) }
        }

    override suspend fun getByEmail(email: String): Franchise? =
        trace("getByEmail") {
            queries.getByEmail(email)
                ?.let { mapper.fromRecord(it) }
        }

    override suspend fun update(franchise: UpdateFranchise): Int {
        // Map the UpdateHolidayEntity to the database model and update the holiday
        return trace("update") {
            updateMapper.toFranchiseRecord(franchise)
                .let { queries.updateFranchise(it) }  // Ensure queries.updateHoliday returns the updated holiday ID
        }
    }
}
