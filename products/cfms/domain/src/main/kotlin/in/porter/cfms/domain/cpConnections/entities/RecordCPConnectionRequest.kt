package `in`.porter.cfms.domain.cpConnections.entities

data class RecordCPConnectionRequest (
    val cpId: Int,
    val franchiseId: String,
    val manifestImageLink : String?
)
