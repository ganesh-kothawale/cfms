package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.FetchOrderResponse
import `in`.porter.cfms.api.models.orders.Order
import `in`.porter.cfms.api.models.common.Pagination
import `in`.porter.cfms.domain.orders.entities.FetchOrdersResponse as DomainFetchOrdersResponse
import javax.inject.Inject

class FetchOrdersResponseMapper @Inject constructor(
    private val orderMapper: OrderMapper
) {
    fun fromDomain(domainResponse: DomainFetchOrdersResponse): FetchOrderResponse {
        return FetchOrderResponse(
            orders = domainResponse.orders.map { orderMapper.fromDomain(it) },
            page = domainResponse.page,
            totalPages = domainResponse.totalPages,
            size = domainResponse.size,
            totalRecords = domainResponse.totalRecords
        )
    }
}