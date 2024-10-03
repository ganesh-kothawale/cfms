package `in`.porter.cfms.api.models.orders

import com.fasterxml.jackson.annotation.JsonProperty


data class UpdateOrderStatusApiRequest(
    val orderStatus: List<String>,
    @JsonProperty("tableType")
    val tableType: String
)