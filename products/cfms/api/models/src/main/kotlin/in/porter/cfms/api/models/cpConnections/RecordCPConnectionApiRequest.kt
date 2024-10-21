package `in`.porter.cfms.api.models.cpConnections

data class RecordCPConnectionApiRequest (
    val cpId: Int,
    val franchiseId: String,
    val manifestImageLink : String?
)
