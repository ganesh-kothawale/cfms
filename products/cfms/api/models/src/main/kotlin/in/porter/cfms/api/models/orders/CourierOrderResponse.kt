package `in`.porter.cfms.api.models.orders

class CourierOrderResponse(
    val code: String,
    val message: String,
    val data: OrderDetails?
)