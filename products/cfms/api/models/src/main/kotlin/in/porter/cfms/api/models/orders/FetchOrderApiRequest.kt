package `in`.porter.cfms.api.models.orders

data class FetchOrderApiRequest(
    val page: Int,
    val size: Int,
    val franchiseId: String?,
)
