package `in`.porter.cfms.data.orders.entities

data class SenderDetails(
    val personalInfo: PersonalInfo,
    val address: Address,
    val location: Location
)