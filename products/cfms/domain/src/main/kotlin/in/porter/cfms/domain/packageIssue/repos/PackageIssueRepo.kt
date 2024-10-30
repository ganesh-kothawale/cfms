package `in`.porter.cfms.domain.packageIssue.repos

import `in`.porter.cfms.domain.packageIssue.entities.DomainListAllPackageIssueRequest
import `in`.porter.cfms.domain.packageIssue.entities.PackageIssue
import `in`.porter.cfms.domain.packageIssue.entities.UpdatePackageIssue
import `in`.porter.cfms.domain.recon.entities.Recon

interface PackageIssueRepo {
    suspend fun countAllPackageIssue(request:DomainListAllPackageIssueRequest): Int
    suspend fun findAllPackageIssue(request:DomainListAllPackageIssueRequest): List<PackageIssue>
    suspend fun findByTaskId(taskId:String) : Boolean
    suspend fun updateAction(request: UpdatePackageIssue): Int
}