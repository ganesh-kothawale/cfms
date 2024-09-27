package `in`.porter.cfms.api.models.courierpartner

data class CreateCourierPartnerApiRequest (
    val cp_id: Int,
    val franchise_id: Int,
    val manifest_image_link : String? = null
)

