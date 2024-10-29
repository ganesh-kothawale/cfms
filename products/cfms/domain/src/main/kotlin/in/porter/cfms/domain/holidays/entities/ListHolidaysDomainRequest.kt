package `in`.porter.cfms.domain.holidays.entities

import java.time.LocalDate

data class ListHolidaysDomainRequest(
    val page: Int,
    val size: Int,
    val franchiseIds: List<String>? = null,
    val backupFranchises: List<String>? = null,
    val leaveType: String? = null,
    val createdDate: LocalDate? = null,
    val updatedDate: LocalDate? = null,
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null
)
