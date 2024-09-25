package courierpartner.mappers

import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.domain.courierpartner.usecases.entities.CreateCourierPartnerRequest
import javax.inject.Inject

class CreateCourierPartnerRequestMapper
@Inject
constructor() {

    companion object {
        const val MANIFEST_LINK = ""
    }

    fun toDomain(req: CreateCourierPartnerApiRequest) = CreateCourierPartnerRequest(
       courierPartnerId = req.courierPartnerId,
        franchiseId =   req.franchiseId,
        manifestImageLink =  req.manifestImageLink?: MANIFEST_LINK
    )
}