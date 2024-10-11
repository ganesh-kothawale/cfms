package `in`.porter.cfms.domain.usecases.entities

data class RecordFranchisePOCRequest(
    val name: String,
    val primaryNumber: String,
    val email: String
)