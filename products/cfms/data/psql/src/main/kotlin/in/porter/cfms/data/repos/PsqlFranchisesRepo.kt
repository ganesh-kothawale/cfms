package `in`.porter.cfms.data.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.franchise.FranchiseQueries
import `in`.porter.cfms.data.franchise.mappers.FranchiseRecordMapper
import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class PsqlFranchisesRepo
@Inject constructor(
    private val queries: FranchiseQueries,
    private val mapper: FranchiseRecordMapper
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
}
