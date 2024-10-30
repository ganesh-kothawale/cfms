package `in`.porter.cfms.api.models.packageIssue

data class UpdatePackageIssueRequest(
    val taskId: String,
    val action: String
)