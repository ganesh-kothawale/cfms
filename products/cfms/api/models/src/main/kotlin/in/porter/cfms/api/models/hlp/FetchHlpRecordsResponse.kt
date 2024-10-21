package `in`.porter.cfms.api.models.hlp

sealed class FetchHlpRecordsResponse {

    data class Success(
        val hlps: List<HlpDetails>,
        val page: Int,
        val size: Int,
        val totalPages: Int,
        val totalRecords: Int
    ) : FetchHlpRecordsResponse()

    data class Error(
        val error: List<ErrorResponse>
    ) : FetchHlpRecordsResponse()
}
