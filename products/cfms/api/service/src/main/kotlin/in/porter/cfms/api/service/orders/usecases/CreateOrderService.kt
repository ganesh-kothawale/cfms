package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.orders.CourierOrderResponse
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequest
import `in`.porter.cfms.api.models.orders.OrderDetails
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderApiRequestMapper
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderRequestMapper
import `in`.porter.cfms.domain.orders.usecases.CreateOrderService
import javax.inject.Inject

class CreateOrderService
@Inject
constructor(
    private val service: CreateOrderService,
    private val apiMapper: CreateOrderApiRequestMapper,
    private val mapper: CreateOrderRequestMapper
) {

    suspend fun invoke(request: CreateOrderApiRequest):CourierOrderResponse {

        try {
            return  apiMapper.toRequestV1(request)
                .let { mapper.toDomain(it) }
                .let { service.invoke(it) }
                .let { mapper.getResponseById(it) }
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid request")
        } catch (e: Exception) {
            throw Exception("Internal server error")
        }
    }
}