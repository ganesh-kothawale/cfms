package `in`.porter.cfms.domain.tasks.entities

import java.time.LocalDate

data class DomainListTasksRequest(
    val page: Int,
    val size: Int,
    val createdDate: LocalDate? = null,
    val updatedDate: LocalDate? = null,
    val taskType: String? = null,
    val franchiseIds: List<String>? = null,
    val orderIds: List<String>? = null,
    val awbNumbers: List<String>? = null,
    val taskStatus: String? = null
)
