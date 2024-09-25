package `in`.porter.cfms.domain.courierpartner.usecases.entities

data class CreateCourierPartnerRequest (
    val courierPartnerId: String,
    val franchiseId: String,
    val manifestImageLink : String?
)