package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.FetchOrderApiRequest as ApiFetchOrderRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersRequest as DomainFetchOrderRequest

class FetchOrderRequestMapper {
    companion object {
        fun fromApi(req: ApiFetchOrderRequest): DomainFetchOrderRequest {
            return DomainFetchOrderRequest(
                page = req.page,
                limit = req.limit,
            )

        }
    }
}