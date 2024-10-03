package `in`.porter.cfms.domain.orders.entities

data class SenderDetails(
    val personalInfo: PersonalInfo,
    val address: Address,
    val location: Location

)

