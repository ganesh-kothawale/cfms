package courierpartner.mappers

import arrow.data.extensions.list.functor.map
import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiRequest
import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiResponse
import `in`.porter.cfms.api.models.courierpartner.Pagination
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsRequest
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsResponse
import javax.inject.Inject

  class FetchCpRecordsMapper
  @Inject
  constructor() {

    fun toDomain(req: FetchCpRecordsApiRequest) = FetchCpRecordsRequest(
      page = req.page,
      pageSize = req.page_size,
      franchiseId = req.franchise_id
    )

    fun fromDomain(response: FetchCpRecordsResponse) = FetchCpRecordsApiResponse(
      data =  response.data.map { CourierPartnerMapper.fromDomain(it) },
      pagination = Pagination(
        currentPage = response.pagination.currentPage,
        pageSize = response.pagination.pageSize,
        totalRecords = response.pagination.totalRecords,
        totalPages = response.pagination.totalPages
      )
    )
  }
