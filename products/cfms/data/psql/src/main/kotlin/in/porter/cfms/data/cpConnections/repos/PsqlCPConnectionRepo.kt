package `in`.porter.cfms.data.cpConnections.repos

import `in`.porter.cfms.data.cpConnections.CPConnectionQueries
import `in`.porter.cfms.data.cpConnections.mappers.CPConnectionRecordMapper
import `in`.porter.cfms.domain.cpConnections.entities.*
import `in`.porter.cfms.domain.cpConnections.repos.CPConnectionRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class PsqlCPConnectionRepo
@Inject
constructor(
    private val queries: CPConnectionQueries,
    private val mapper: CPConnectionRecordMapper
) : Traceable, CPConnectionRepo {
    companion object : Logging

    override suspend fun create(request: RecordCPConnectionRequest): Unit = trace("create") {
        mapper.toRecordData(request)
            .let { queries.record(it) }
    }

    override suspend fun getByPagination(request: FetchCPConnectionsRequest): List<CPConnection> =
        trace("getByPagination") {
            val offset = request.size * (request.page - 1)
            queries.getByPagination(request.size, offset)
                .map { mapper.fromRecord(it) }
        }

    override suspend fun getByPaginationByFranchiseId(request: FetchCPConnectionsRequest): List<CPConnection> =
        trace("getByPaginationByFranchiseId") {
            val offset = request.size * (request.page - 1)
            queries.getByPaginationByFranchiseId(request.size, offset, request.franchiseId!!)
                .map { mapper.fromRecord(it) }
        }


    override suspend fun getAllCount(): Int = trace("getAllCount") {
        queries.getCpCount()
    }

    override suspend fun getAllCountByFranchiseId(franchiseId: String): Int = trace("getAllCountByFranchiseId") {
        queries.getCpCountByFranchiseId(franchiseId)
    }

}
