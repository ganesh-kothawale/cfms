package `in`.porter.cfms.api.models.orders

data class ItemDetails(
    val materialType: String,
    val materialWeight: Int,
    val dimensions: Dimensions?
)

