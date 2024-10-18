package `in`.porter.cfms.data.hlp.mappers

import `in`.porter.cfms.data.hlp.records.HlpRecord
import `in`.porter.cfms.data.hlp.records.HlpRecordData
import `in`.porter.cfms.domain.hlp.entities.HlpDetails
import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft
import javax.inject.Inject

class HlpRecordMapper
@Inject
constructor() {

    fun toRecord(details: HlpDetails) = HlpRecord(
        id = details.id,
        data = toData(details),
        createdAt = details.createdAt,
        updatedAt = details.updatedAt
    )

    fun toData(draft: HlpDetailsDraft) = HlpRecordData(
        hlpOrderId = draft.hlpOrderId,
        hlpOrderStatus = draft.hlpOrderStatus,
        otp = draft.otp,
        riderName = draft.riderName,
        riderNumber = draft.riderNumber,
        vehicleType = draft.vehicleType,
        franchiseId = draft.franchiseId
    )

    private fun toData(details: HlpDetails) = HlpRecordData(
        hlpOrderId = details.hlpOrderId,
        hlpOrderStatus = details.hlpOrderStatus,
        otp = details.otp,
        riderName = details.riderName,
        riderNumber = details.riderNumber,
        vehicleType = details.vehicleType,
        franchiseId = details.franchiseId
    )
}
