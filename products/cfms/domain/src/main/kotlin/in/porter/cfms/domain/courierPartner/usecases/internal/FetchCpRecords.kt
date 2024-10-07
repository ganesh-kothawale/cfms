package `in`.porter.cfms.domain.courierPartner.usecases.internal

import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.*
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class FetchCpRecords
  @Inject
  constructor(
    private val repo: CourierPartnerRepo,
) : Traceable{
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
      val records = repo.fetchCpRecords(request)
      val recordsUpdated: MutableList<CourierPartnerDomain> = mutableListOf()


      records.forEach { courierPartnerDomain ->
        try {
          val name = repo.getCpName(courierPartnerDomain.cpId) ?: throw CfmsException("entry not found with cpId")
          val courierPartner = CourierPartnerDomain(
            id = courierPartnerDomain.id,
            createdAt = courierPartnerDomain.createdAt,
            cpId = courierPartnerDomain.cpId,
            franchiseId = courierPartnerDomain.franchiseId,
            manifestImageUrl = courierPartnerDomain.manifestImageUrl,
            courierPartnerName = name
            )
          recordsUpdated.add(courierPartner)
        }
        catch (e: CfmsException){
          throw CfmsException(e.message)
        }
      }
      generateResponse(recordsUpdated, totalCount, request)
    } catch (e: Exception) {
      throw e
    }
  }
}