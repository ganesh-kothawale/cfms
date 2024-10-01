package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.UpdateOrderStatusApiRequest
import `in`.porter.cfms.domain.orders.entities.UpdateOrderStatusRequest

class UpdateOrderStatusApiRequestMapper {

    fun toDomain(req: UpdateOrderStatusApiRequest, orderId: String): UpdateOrderStatusRequest =
        UpdateOrderStatusRequest(
            orderId = orderId,
            status = req.orderStatus

        )
}