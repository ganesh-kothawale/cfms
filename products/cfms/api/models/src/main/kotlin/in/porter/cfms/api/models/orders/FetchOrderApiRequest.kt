package `in`.porter.cfms.api.models.orders

data class FetchOrderApiRequest(
    val page: Int,
    val limit: Int,
    val franchiseId: String?,
)
