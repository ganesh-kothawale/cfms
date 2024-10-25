package `in`.porter.cfms.domain.packageIssue.entities

import java.time.Instant

data class PackageIssue(
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
    val createdAt: Instant,
    val updatedAt: Instant
)
