package `in`.porter.cfms.domain.packageIssue.repos

import `in`.porter.cfms.domain.packageIssue.entities.PackageIssue

interface PackageIssueRepo {
    suspend fun countAllPackageIssue(returnRequested: Boolean? = null): Int
    suspend fun findAllPackageIssue(page: Int, size: Int, returnRequested: Boolean? = null): List<PackageIssue>
}