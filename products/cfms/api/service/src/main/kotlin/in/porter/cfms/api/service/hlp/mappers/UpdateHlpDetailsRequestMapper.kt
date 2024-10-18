package `in`.porter.cfms.api.service.hlp.mappers

import `in`.porter.cfms.api.models.hlp.RecordHlpDetailsRequest
import `in`.porter.cfms.api.models.hlp.UpdateHlpDetailsRequest as UpdateHlpDetailsRequestApi
import `in`.porter.cfms.domain.hlp.entities.UpdateHlpDetailsRequest
import javax.inject.Inject

class UpdateHlpDetailsRequestMapper
@Inject
constructor() {

    fun toDomain(req: UpdateHlpDetailsRequestApi): UpdateHlpDetailsRequest {
        validateRequest(req)

        return UpdateHlpDetailsRequest(
            hlpOrderId = req.hlpOrderId,
            hlpOrderStatus = req.hlpOrderStatus,
            otp = req.otp,
            riderName = req.riderName,
            riderNumber = req.riderNumber,
            vehicleType = req.vehicleType,
        )
    }

    private fun validateRequest(req: UpdateHlpDetailsRequestApi) {
        req.let {
            if (it.hlpOrderId.isBlank()) throw IllegalArgumentException("hlpOrderId cannot be blank")
            if (it.hlpOrderStatus != null && it.hlpOrderStatus!!.isBlank()) throw IllegalArgumentException("hlpOrderStatus cannot be blank")
            if (it.otp != null && it.otp!!.isBlank()) throw IllegalArgumentException("otp cannot be blank")
            if (it.riderName != null && it.riderName!!.isBlank()) throw IllegalArgumentException("riderName cannot be blank")
            if (it.riderNumber != null && it.riderNumber!!.isBlank()) throw IllegalArgumentException("riderNumber cannot be blank")
            if (it.vehicleType != null && it.vehicleType!!.isBlank()) throw IllegalArgumentException("vehicleType cannot be blank")
        }
    }

}
