package `in`.porter.cfms.api.service.hlp.mappers

import `in`.porter.cfms.domain.hlp.entities.HlpDetails
import `in`.porter.cfms.api.models.hlp.HlpDetails as HlpDetailsApi
import javax.inject.Inject

class HlpDetailsMapper
@Inject
constructor() {

    fun fromDomain(req: HlpDetails) = HlpDetailsApi(
        hlpOrderId = req.hlpOrderId,
        hlpOrderStatus = req.hlpOrderStatus,
        otp = req.otp,
        riderName = req.riderName,
        riderNumber = req.riderNumber,
        vehicleType = req.vehicleType,
        franchiseId = req.franchiseId,
    )
}
