package `in`.porter.cfms.data.orders.entities

data class ItemDetails(
    val materialType: String,
    val materialWeight: Int,
    val dimensions: Dimensions?
)