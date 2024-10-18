package `in`.porter.cfms.api.service.hlp.mappers

import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft
import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsRequest as RecordHlpDetailsRequestApi
import javax.inject.Inject

class RecordHlpDetailsRequestMapper
@Inject
constructor() {

    fun toDomain(req: RecordHlpDetailsRequestApi): HlpDetailsDraft {
        validateRequest(req)

        return HlpDetailsDraft(
            hlpOrderId = req.hlpOrderId,
            hlpOrderStatus = req.hlpOrderStatus,
            otp = req.otp,
            riderName = req.riderName,
            riderNumber = req.riderNumber,
            vehicleType = req.vehicleType,
            franchiseId = req.franchiseId,
        )
    }

    private fun validateRequest(req: RecordHlpDetailsRequestApi) = req.let {
        if (it.hlpOrderId.isBlank()) throw IllegalArgumentException("hlpOrderId cannot be blank")
        if (it.hlpOrderStatus != null && it.hlpOrderStatus!!.isBlank()) throw IllegalArgumentException("hlpOrderStatus cannot be blank")
        if (it.otp != null && it.otp!!.isBlank()) throw IllegalArgumentException("otp cannot be blank")
        if (it.riderName != null && it.riderName!!.isBlank()) throw IllegalArgumentException("riderName cannot be blank")
        if (it.riderNumber != null && it.riderNumber!!.isBlank()) throw IllegalArgumentException("riderNumber cannot be blank")
        if (it.vehicleType != null && it.vehicleType!!.isBlank()) throw IllegalArgumentException("vehicleType cannot be blank")
        if (it.franchiseId.isBlank()) throw IllegalArgumentException("franchiseId cannot be blank")
    }

}
