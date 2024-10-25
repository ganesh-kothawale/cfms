package `in`.porter.cfms.domain.recon.entities

data class ReconTaskResult(
    val data: List<ReconTask>,
    val totalRecords: Int
)