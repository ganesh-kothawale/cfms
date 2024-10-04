package `in`.porter.cfms.api.CreateCourierPartner.factories

import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest

class CreateCourierPartnerTestFactory {
    fun buildCreateCourierPartnerRequest(
    franchiseId : Int = 12,
    courierPartnerId : Int = 13,
    manifestImageLink : String? = "link"
    ) = CreateCourierPartnerApiRequest(
        cpId = courierPartnerId,
        franchiseId = franchiseId,
        manifestImageLink = manifestImageLink
    )

}