package `in`.porter.cfms.api.models.courierpartner

data class CreateCourierPartnerApiRequest (
    val cpId: Int,
    val franchiseId: Int,
    val manifestImageLink : String? = null
)

