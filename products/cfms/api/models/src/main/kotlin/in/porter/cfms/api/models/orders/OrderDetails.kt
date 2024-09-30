package `in`.porter.cfms.api.models.orders

import com.fasterxml.jackson.annotation.JsonProperty


data class OrderDetails(
    @JsonProperty("recordId")
    val recordId : String
)