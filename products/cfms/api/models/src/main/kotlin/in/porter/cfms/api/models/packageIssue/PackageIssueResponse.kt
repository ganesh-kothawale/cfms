package `in`.porter.cfms.api.models.packageIssue

data class PackageIssueResponse(
    val reconId: String,
    val orderId: String,
    val taskId: String,
    val teamId: String,
    val status: String?,
    val action: String?,
    val senderName: String,
    val senderMobile: String,
    val franchiseId: String,
    val awbNumber: String?,
    val returnRequested: Boolean?,
    val returnImageUrl: List<String>?,
    val createdAt: String,
    val updatedAt: String
)
