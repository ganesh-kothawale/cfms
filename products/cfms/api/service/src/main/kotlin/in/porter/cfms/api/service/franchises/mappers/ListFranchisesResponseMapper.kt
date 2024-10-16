package `in`.porter.cfms.api.service.franchises.mappers

import `in`.porter.cfms.api.models.franchises.AddressResponse
import `in`.porter.cfms.api.models.franchises.FranchiseeResponse
import `in`.porter.cfms.api.models.franchises.LatLngResponse
import `in`.porter.cfms.api.models.franchises.ListFranchisesResponse
import `in`.porter.cfms.api.models.franchises.PointOfContactResponse
import `in`.porter.cfms.domain.franchise.entities.Address
import `in`.porter.cfms.domain.franchise.entities.ListFranchise
import `in`.porter.cfms.domain.franchise.entities.PointOfContact

object ListFranchisesResponseMapper {
    fun toResponse(
        franchises: List<ListFranchise>,
        page: Int,
        size: Int,
        totalPages: Int,
        totalRecords: Int
    ): ListFranchisesResponse {
        return ListFranchisesResponse(
            franchises = franchises.map { toFranchiseeResponse(it) },
            page = page,
            size = size,
            totalPages = totalPages,
            totalRecords = totalRecords
        )
    }

    private fun toFranchiseeResponse(franchise: ListFranchise): FranchiseeResponse {
        return FranchiseeResponse(
            franchiseId = franchise.franchiseId,
            address = toAddressResponse(franchise.address),
            poc = toPointOfContactResponse(franchise.poc),
            radiusCovered = franchise.radiusCovered,
            hlpEnabled = franchise.hlpEnabled,
            isActive = franchise.isActive,
            daysOfTheWeek = franchise.daysOfTheWeek,
            cutOffTime = franchise.cutOffTime,
            startTime = franchise.startTime,
            endTime = franchise.endTime,
            holidays = franchise.holidays,
            porterHubName = franchise.porterHubName,
            franchiseGst = franchise.franchiseGst,
            franchisePan = franchise.franchisePan,
            franchiseCanceledCheque = franchise.franchiseCanceledCheque,
            courierPartners = franchise.courierPartners,
            kamUser = franchise.kamUser,
            createdAt = franchise.createdAt.toString(),
            updatedAt = franchise.updatedAt.toString()
        )
    }

    private fun toAddressResponse(address: Address): AddressResponse {
        return AddressResponse(
            lat = address.lat,
            lng = address.lng,
            address = address.address,
            city = address.city,
            state = address.state,
            pincode = address.pincode,
        )
    }

    private fun toPointOfContactResponse(poc: PointOfContact): PointOfContactResponse {
        return PointOfContactResponse(
            name = poc.name,
            mobile = poc.mobile,
            email = poc.email
        )
    }
}