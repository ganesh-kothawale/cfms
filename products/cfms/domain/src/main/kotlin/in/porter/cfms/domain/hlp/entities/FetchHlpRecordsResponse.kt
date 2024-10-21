package `in`.porter.cfms.domain.hlp.entities

data class FetchHlpRecordsResponse(
    val hlps: List<HlpDetails>,
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalRecords: Int
)
