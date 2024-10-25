package `in`.porter.cfms.domain.packageIssue.entities

data class PackageIssueResult(
    val data: List<PackageIssue>,
    val totalRecords: Int
)
