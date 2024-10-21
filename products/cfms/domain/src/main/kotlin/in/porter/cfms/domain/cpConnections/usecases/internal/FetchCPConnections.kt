package `in`.porter.cfms.domain.cpConnections.usecases.internal

import `in`.porter.cfms.domain.cpConnections.entities.*
import `in`.porter.cfms.domain.cpConnections.repos.CPConnectionRepo
import `in`.porter.cfms.domain.courierPartners.repos.CourierPartnersRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class FetchCPConnections
@Inject
constructor(
  private val repo: CPConnectionRepo,
  private val courierPartnersRepo: CourierPartnersRepo,
) : Traceable {

  suspend fun invoke(request: FetchCPConnectionsRequest): FetchCPConnectionsResponse {
    val totalRecords = repo.getAllCount()
    val cpConnections = repo.getByPagination(request)
    val cpIds = cpConnections.map { it.cpId }.distinct()
    val cps = courierPartnersRepo.getByIds(cpIds)

    return FetchCPConnectionsResponse(
      cpConnections = cpConnections,
      cps = cps,
      page = request.page,
      size = request.size,
      totalPages = (totalRecords + request.size - 1) / request.size,
      totalRecords = totalRecords,
    )
  }
}
