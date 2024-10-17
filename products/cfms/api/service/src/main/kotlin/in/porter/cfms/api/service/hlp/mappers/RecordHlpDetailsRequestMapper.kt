package `in`.porter.cfms.api.service.hlp.mappers

import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft
import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsRequest as RecordHlpDetailsRequestApi
import javax.inject.Inject

class RecordHlpDetailsRequestMapper
@Inject
constructor() {

    fun toDomain(req: RecordHlpDetailsRequestApi) = HlpDetailsDraft(
        hlpOrderId = req.hlpOrderId,
        hlpOrderStatus = req.hlpOrderStatus,
        otp = req.otp,
        riderName = req.riderName,
        riderNumber = req.riderNumber,
        vehicleType = req.vehicleType,
        franchiseId = req.franchiseId,
    )

}
