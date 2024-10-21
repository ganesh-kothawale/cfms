package `in`.porter.cfms.api.models.cpConnections

data class RecordCPConnectionResponse(
  val data: Data? = null,
  val error: List<ErrorResponse> = emptyList()
)

data class Data(
  val message: String
)

data class ErrorResponse(
  val message: String,
  val details: String
)
