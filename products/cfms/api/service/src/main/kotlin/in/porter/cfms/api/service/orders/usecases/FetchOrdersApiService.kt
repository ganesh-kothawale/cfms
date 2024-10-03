package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.orders.FetchOrderApiRequest
import `in`.porter.cfms.api.models.orders.FetchOrderResponse
import `in`.porter.cfms.api.service.orders.mappers.FetchOrderRequestMapper
import `in`.porter.cfms.api.service.orders.mappers.FetchOrdersResponseMapper
import `in`.porter.cfms.domain.orders.usecases.FetchOrdersService
import javax.inject.Inject

class FetchOrdersApiService @Inject constructor(
    private val toDomain: FetchOrdersService,
    private val fetchOrderRequestMapper: FetchOrderRequestMapper,
    private val fetchOrdersResponseMapper: FetchOrdersResponseMapper
) {

    suspend fun invoke(request: FetchOrderApiRequest): FetchOrderResponse {
        return fetchOrderRequestMapper.fromApi(request)
            .let { domainRequest -> toDomain.invoke(domainRequest) }
            .let { fetchOrdersResponseMapper.fromDomain(it) }
    }
}