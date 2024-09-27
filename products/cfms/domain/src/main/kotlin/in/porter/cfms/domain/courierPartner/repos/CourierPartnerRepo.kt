package `in`.porter.cfms.domain.courierPartner.repos

import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest

interface CourierPartnerRepo {

    suspend fun create(courierPartnerRequest: CreateCourierPartnerRequest): Int
    //suspend fun getById(courierPartnerId: Int): CourierPartnerRecord?
}