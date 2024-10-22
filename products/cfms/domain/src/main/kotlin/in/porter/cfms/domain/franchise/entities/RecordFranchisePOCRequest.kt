package `in`.porter.cfms.domain.franchise.entities

data class RecordFranchisePOCRequest(
    val name: String,
    val primaryNumber: String,
    val email: String
)