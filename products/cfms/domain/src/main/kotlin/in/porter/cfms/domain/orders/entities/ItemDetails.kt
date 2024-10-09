package `in`.porter.cfms.domain.orders.entities

data class ItemDetails(
    val materialType: String,
    val materialWeight: Int,
    val dimensions: Dimensions?
)

