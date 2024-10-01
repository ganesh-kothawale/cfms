package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.orders.FetchOrderResponse
import `in`.porter.cfms.api.models.orders.UpdateOrderStatusApiRequest
import `in`.porter.cfms.api.service.orders.mappers.FetchOrdersResponseMapper
import `in`.porter.cfms.api.service.orders.mappers.UpdateOrderStatusApiRequestMapper
import `in`.porter.cfms.domain.orders.usecases.UpdateOrderStatusService
import javax.inject.Inject

class UpdateOrderStatusApiService @Inject constructor(
    private val serbice: UpdateOrderStatusService,
    private val mapper: UpdateOrderStatusApiRequestMapper
    val responseMapper: FetchOrdersResponseMapper
) {

    suspend fun invoke(request: UpdateOrderStatusApiRequest, orderId: String): FetchOrderResponse = try {
        mapper.toDomain(request, orderId)
            .let { serbice.invoke(it) }
            .let { responseMapper.fromDomain(it) }

    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid request: ${e.message}")
    } catch (e: Exception) {
        throw Exception("Internal server error: ${e.message}")
    }
}