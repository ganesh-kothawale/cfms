package `in`.porter.cfms.api.models.orders

data class SenderDetails(
    val personalInfo: PersonalInfo,
    val address: Address,
    val location: Location
)

