package `in`.porter.cfms.domain.courierPartner.usecases.internal

import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.*
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class FetchCpRecords
  @Inject
  constructor(
    private val repo: CourierPartnerRepo,
) {
  private fun generateResponse(listOfCourierPartners: List<CourierPartnerDomain>, totalCount: Int, request: FetchCpRecordsRequest): FetchCpRecordsResponse {
    val pagination = Pagination(
      currentPage = request.page + 1,
      pageSize = request.pageSize,
      totalPages = (totalCount + request.pageSize - 1) / request.pageSize,
      totalRecords = totalCount
    )
    return FetchCpRecordsResponse(
      data = listOfCourierPartners,
      pagination = pagination
    )
  }

  suspend fun invoke(request: FetchCpRecordsRequest): FetchCpRecordsResponse {
    return try {
      // gives total count
      val totalCount = repo.getCpCount()
      // here response is mapped to Domain response in fetchCpRecordsItself
      val orders = repo.fetchCpRecords(request)
      // request is passed in below for using in pagination data calculation
      generateResponse(orders, totalCount, request)
    } catch (e: Exception) {
      println("Error executing fetchCpRecords: ${e.message}")
      throw e
    }
  }
}