package `in`.porter.cfms.api.service.packageIssue.mappers

import `in`.porter.cfms.api.models.packageIssue.ListPackageIssueResponse
import `in`.porter.cfms.api.models.packageIssue.PackageIssueResponse
import `in`.porter.cfms.domain.packageIssue.entities.PackageIssue
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ListPackageIssueResponseMapper @Inject constructor() {

    val formatter = DateTimeFormatter.ISO_INSTANT

    fun toResponse(
        packageIssues: List<PackageIssue>,  // List of PackageIssue (domain entity)
        page: Int,
        size: Int,
        totalPages: Int,
        totalRecords: Int
    ): ListPackageIssueResponse {
        return ListPackageIssueResponse(
            packageIssues = packageIssues.map { toPackageIssueResponse(it) },  // Map PackageIssue to PackageIssueResponse
            page = page,
            size = size,
            totalRecords = totalRecords,
            totalPages = totalPages
        )
    }

    private fun toPackageIssueResponse(packageIssue: PackageIssue): PackageIssueResponse {
        return PackageIssueResponse(
            reconId = packageIssue.reconId,
            orderId = packageIssue.orderId,
            taskId = packageIssue.taskId,
            teamId = packageIssue.teamId,
            status = packageIssue.status,
            action = packageIssue.action,
            senderName = packageIssue.senderName,
            senderMobile = packageIssue.senderMobile,
            franchiseId = packageIssue.franchiseId,
            awbNumber = packageIssue.awbNumber,
            returnRequested = packageIssue.returnRequested,
            returnImageUrl = packageIssue.returnImageUrl,
            createdAt = formatter.format(packageIssue.createdAt),
            updatedAt = formatter.format(packageIssue.updatedAt)
        )
    }
}
