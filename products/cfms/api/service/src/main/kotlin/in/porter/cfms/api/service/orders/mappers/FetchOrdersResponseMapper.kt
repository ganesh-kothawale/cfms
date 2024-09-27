package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.FetchOrderResponse
import `in`.porter.cfms.api.models.orders.Order
import `in`.porter.cfms.api.models.common.Pagination
import `in`.porter.cfms.domain.orders.entities.FetchOrdersResponse as DomainFetchOrdersResponse

object FetchOrdersResponseMapper {
    fun fromDomain(domainResponse: DomainFetchOrdersResponse): FetchOrderResponse {
        return FetchOrderResponse(
            orders = domainResponse.orders.map { OrderMapper.fromDomain(it) },
            pagination = Pagination(
                page = domainResponse.pagination.page,
                totalPages = domainResponse.pagination.totalPages,
                limit = domainResponse.pagination.limit
            )
        )
    }
}