package `in`.porter.cfms.api.models.recon

data class ListReconResponse(
    val recons: List<ReconResponse>,
    val page: Int,
    val size: Int,
    val totalRecords: Int,
    val totalPages: Int
)