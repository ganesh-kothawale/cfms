package `in`.porter.cfms.api.models.hlp

data class RecordHlpDetailsResponse(
    val data: Data? = null,
    val error: List<ErrorResponse> = emptyList()
)

data class Data(
    val message: String,
    val hlpOrderId: String
)

data class ErrorResponse(
    val message: String,
    val details: String
)
