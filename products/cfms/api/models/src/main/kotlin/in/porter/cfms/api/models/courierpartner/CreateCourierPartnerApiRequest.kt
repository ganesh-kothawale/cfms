package `in`.porter.cfms.api.models.courierpartner

data class CreateCourierPartnerApiRequest (
    val courierPartnerId: String,
    val franchiseId: String,
    val manifestImageLink : String?
)

