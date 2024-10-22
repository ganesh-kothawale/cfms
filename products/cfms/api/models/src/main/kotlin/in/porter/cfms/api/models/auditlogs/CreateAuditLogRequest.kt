package `in`.porter.cfms.api.models.auditlogs

data class CreateAuditLogRequest(
    val entityId: String,
    val entityType: String,
    val status: String,
    val message: String?,
    val updatedBy: Int
)
