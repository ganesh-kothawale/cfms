package `in`.porter.cfms.data.hlp.mappers

import `in`.porter.cfms.data.hlp.records.HlpRecordData
import `in`.porter.cfms.data.hlp.records.UpdateHlpRecord
import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft
import `in`.porter.cfms.domain.hlp.entities.UpdateHlpDetailsRequest
import javax.inject.Inject

class HlpRecordMapper
@Inject
constructor() {

    fun toData(draft: HlpDetailsDraft) = HlpRecordData(
        hlpOrderId = draft.hlpOrderId,
        hlpOrderStatus = draft.hlpOrderStatus,
        otp = draft.otp,
        riderName = draft.riderName,
        riderNumber = draft.riderNumber,
        vehicleType = draft.vehicleType,
        franchiseId = draft.franchiseId
    )

    fun toUpdateHlpRecord(req: UpdateHlpDetailsRequest) = UpdateHlpRecord(
        hlpOrderId = req.hlpOrderId,
        hlpOrderStatus = req.hlpOrderStatus,
        otp = req.otp,
        riderName = req.riderName,
        riderNumber = req.riderNumber,
        vehicleType = req.vehicleType,
    )
}
