package `in`.porter.cfms.domain.recon.entities

data class ReconResult(
    val data: List<Recon>,
    val totalRecords: Int
)