package `in`.porter.cfms.api.models.franchises

data class ListFranchisesResponse(
    val franchisees: List<FranchiseeResponse>,
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalRecords: Int
)
