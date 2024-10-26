package `in`.porter.cfms.api.models.packageIssue

import com.fasterxml.jackson.annotation.JsonProperty

data class ListPackageIssueResponse(
    @JsonProperty("package-issues")
    val packageIssues: List<PackageIssueResponse>,
    val page: Int,
    val size: Int,
    val totalRecords: Int,
    val totalPages: Int
)