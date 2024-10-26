package `in`.porter.cfms.domain.franchise.entities

import java.time.LocalDate

data class DomainListFranchisesRequest(
    val page: Int,
    val size: Int,
    val createdDate: LocalDate? = null,
    val updatedDate: LocalDate? = null,
    val franchiseId: String? = null,
    val pocPrimaryNumber: String? = null,
    val emailId: String? = null,
    val porterHubName: String? = null,
    val status: String? = null,
    val kam: String? = null,
    val city: String? = null,
    val state: String? = null,
    val pincode: Int? = null,
    val geoRegionId: String? = null,
    val radiusCoverage: Int? = null
)
