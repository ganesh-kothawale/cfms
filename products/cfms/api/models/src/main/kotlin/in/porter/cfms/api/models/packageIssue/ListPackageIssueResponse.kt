package `in`.porter.cfms.api.models.packageIssue

import com.fasterxml.jackson.annotation.JsonProperty

data class ListPackageIssueResponse(
    @JsonProperty("package_issues")
    val packageIssues: List<PackageIssueResponse>,
    val page: Int,
    val size: Int,
    val totalRecords: Int,
    val totalPages: Int
)