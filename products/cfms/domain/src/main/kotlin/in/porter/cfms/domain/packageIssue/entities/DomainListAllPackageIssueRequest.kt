package `in`.porter.cfms.domain.packageIssue.entities

import java.time.LocalDate

data class DomainListAllPackageIssueRequest(
    val page: Int,
    val size: Int,
    val createdDate: LocalDate? = null,
    val updatedDate: LocalDate? = null,
    val returnRequested: Boolean? = true,
    val orderId: List<String>? = null,
    val awbNo: List<String>? = null,
    val franchiseId: List<String>? = null,
    val action: String? = null
)
