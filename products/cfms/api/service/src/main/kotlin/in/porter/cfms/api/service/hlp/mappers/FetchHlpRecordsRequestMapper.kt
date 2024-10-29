package `in`.porter.cfms.api.service.hlp.mappers

import `in`.porter.cfms.domain.hlp.entities.FetchHlpRecordsRequest
import `in`.porter.cfms.api.models.hlp.FetchHlpRecordsRequest as FetchHlpRecordsRequestApi
import javax.inject.Inject

class FetchHlpRecordsRequestMapper
@Inject
constructor() {

    fun toDomain(req: FetchHlpRecordsRequestApi): FetchHlpRecordsRequest {
        validateRequest(req)
        return FetchHlpRecordsRequest(
            page = req.page,
            size = req.size,
            createdDate = req.createdDate,
            updatedDate = req.updatedDate,
            driverNames = req.driverNames,
            driverNumber = req.driverNumber,
            hlpOrderIds = req.hlpOrderIds,
            hlpOrderStatus = req.hlpOrderStatus,
            franchiseIds = req.franchiseIds,
            vehicleTypes = req.vehicleTypes
        )
    }

    private fun validateRequest(req: FetchHlpRecordsRequestApi) {
        if (req.page < 1 || req.size < 1 || req.size > 100)
            throw IllegalArgumentException("Page must be a positive integer, and size must be between 1 and 100.")
    }
}
