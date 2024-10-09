package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.UpdateOrderStatusApiRequest
import `in`.porter.cfms.domain.orders.entities.UpdateOrderStatusRequest
import javax.inject.Inject

class UpdateOrderStatusApiRequestMapper
@Inject constructor() {

    fun toDomain(req: UpdateOrderStatusApiRequest, orderId: String?): UpdateOrderStatusRequest =
        UpdateOrderStatusRequest(
            orderId = orderId?.let { it.toInt() } ?: throw IllegalArgumentException("Invalid order id"),
            status = req.orderStatus.first()

        )
}