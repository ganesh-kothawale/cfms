package `in`.porter.cfms.api.FetchCourierPartner.factories

import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiRequest

class FetchCourierPartnersTestFactory {
    fun buildFetchCourierPartnersRequest(
        page: Int = 1,
        pageSize: Int = 2,
        franchiseId: Int = 3
    ) = FetchCpRecordsApiRequest(
        page = page,
        page_size = pageSize,
        franchise_id = franchiseId
    )
}
