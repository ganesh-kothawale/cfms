package `in`.porter.cfms.domain.packageIssue.repos

import `in`.porter.cfms.domain.packageIssue.entities.DomainListAllPackageIssueRequest
import `in`.porter.cfms.domain.packageIssue.entities.PackageIssue

interface PackageIssueRepo {
    suspend fun countAllPackageIssue(request:DomainListAllPackageIssueRequest): Int
    suspend fun findAllPackageIssue(request:DomainListAllPackageIssueRequest): List<PackageIssue>
}