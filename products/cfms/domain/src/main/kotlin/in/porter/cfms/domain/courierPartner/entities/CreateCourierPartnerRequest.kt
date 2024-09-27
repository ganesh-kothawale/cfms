package `in`.porter.cfms.domain.courierPartner.entities

data class CreateCourierPartnerRequest (
    val courierPartnerId: Int,
    val franchiseId: Int,
    val manifestImageLink : String?
)