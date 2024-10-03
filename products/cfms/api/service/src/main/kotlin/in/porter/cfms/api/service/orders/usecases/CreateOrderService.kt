package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.orders.CourierOrderResponse
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequestV2
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderRequestMapper
import `in`.porter.cfms.domain.orders.usecases.CreateOrder
import javax.inject.Inject

class CreateOrderService
@Inject
constructor(
    private val service: CreateOrder,
    private val mapper: CreateOrderRequestMapper
) {

    suspend fun invoke(request: CreateOrderApiRequestV2): CourierOrderResponse {
        try {
            return mapper.toDomain(request)
                .let { service.invoke(it) }
                .let { mapper.getResponseById(it) }
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid request")
        } catch (e: Exception) {
            throw Exception("Internal server error")
        }
    }
}