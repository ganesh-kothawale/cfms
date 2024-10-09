package `in`.porter.cfms.api.models.orders

data class AssociationDetails(
    val franchiseId: String,
    val teamId: String?  // nullable
)