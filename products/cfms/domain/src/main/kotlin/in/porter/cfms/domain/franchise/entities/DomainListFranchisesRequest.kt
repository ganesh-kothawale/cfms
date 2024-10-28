package `in`.porter.cfms.domain.franchise.entities

import java.time.LocalDate

data class DomainListFranchisesRequest(
    val page: Int,
    val size: Int,
    val createdDate: LocalDate? = null,
    val updatedDate: LocalDate? = null,
    val franchiseIds: List<String>? = null,
    val pocPrimaryNumber: String? = null,
    val emailId: String? = null,
    val porterHubNames: List<String>? = null,
    val status: String? = null,
    val kams: List<String>? = null,
    val cities: List<String>? = null,
    val states: List<String>? = null,
    val pincode: Int? = null,
    val geoRegionId: String? = null,
    val radiusCoverage: Int? = null
)
