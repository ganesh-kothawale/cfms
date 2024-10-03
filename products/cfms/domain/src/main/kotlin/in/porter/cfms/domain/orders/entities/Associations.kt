package `in`.porter.cfms.domain.orders.entities

data class AssociationDetails(
    val franchiseId: String,
    val teamId: String?  // nullable
)