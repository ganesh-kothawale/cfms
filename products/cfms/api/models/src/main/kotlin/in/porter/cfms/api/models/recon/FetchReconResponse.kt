package `in`.porter.cfms.api.models.recon

class FetchReconResponse (
    val recons: List<ReconTaskResponse>,
    val page: Int,
    val size: Int,
    val totalRecords: Int,
    val totalPages: Int
)