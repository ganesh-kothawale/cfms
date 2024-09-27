package courierpartner.mappers

import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import javax.inject.Inject

class CreateCourierPartnerRequestMapper
@Inject
constructor() {
    companion object {
        const val MANIFEST_LINK = ""
    }

    fun toDomain(req: CreateCourierPartnerApiRequest) = CreateCourierPartnerRequest(
        courierPartnerId = req.cp_id,
        franchiseId =   req.franchise_id,
        manifestImageLink =  req.manifest_image_link?: MANIFEST_LINK
    )
}