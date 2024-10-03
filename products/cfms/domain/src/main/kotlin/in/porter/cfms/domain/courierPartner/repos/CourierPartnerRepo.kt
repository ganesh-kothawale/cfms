package `in`.porter.cfms.domain.courierPartner.repos

import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerDomain
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsRequest

interface CourierPartnerRepo {

    suspend fun create(courierPartnerRequest: CreateCourierPartnerRequest): Int
    suspend fun fetchCpRecords(request: FetchCpRecordsRequest): List<CourierPartnerDomain>
    suspend fun getCpCount(): Int
}