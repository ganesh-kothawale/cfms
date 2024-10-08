package `in`.porter.cfms.api.models.franchises

data class Data(
    val message: String,
    val franchise_id: String
)

data class ErrorResponse(
    val message: String,
    val details: String
)

data class RecordFranchiseDetailsResponse(
    val data: Data? = null,
    val error: List<ErrorResponse> = emptyList()
)
