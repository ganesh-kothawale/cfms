package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.FetchOrderResponse
import `in`.porter.cfms.api.models.orders.Order
import `in`.porter.cfms.api.models.common.Pagination
import `in`.porter.cfms.domain.orders.entities.Order as DomainOrder
import `in`.porter.cfms.domain.common.entities.Pagination as DomainPagination

class FetchOrdersResponseMapper {
    companion object {
        fun fromDomain(domainOrders: List<DomainOrder>, domainPagination: DomainPagination): FetchOrderResponse =
            FetchOrderResponse(
                orders = domainOrders.map { OrderMapper.fromDomain(it) },
                pagination = Pagination(
                    page = domainPagination.page,
                    totalPages = domainPagination.totalPages,
                    limit = domainPagination.limit
                )
            )
    }
}