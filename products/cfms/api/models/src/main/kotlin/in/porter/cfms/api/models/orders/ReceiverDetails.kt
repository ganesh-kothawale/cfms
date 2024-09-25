package `in`.porter.cfms.api.models.orders

data class ReceiverDetails(
    val personalInfo: PersonalInfo,
    val address: Address,
)