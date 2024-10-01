package `in`.porter.cfms.api.models.orders

data class UpdateOrderStatusApiRequest(
    val orderStatus: List<String>

)
