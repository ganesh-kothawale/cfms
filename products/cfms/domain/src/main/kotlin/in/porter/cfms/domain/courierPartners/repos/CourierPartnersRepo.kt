package `in`.porter.cfms.domain.courierPartners.repos

import `in`.porter.cfms.domain.courierPartners.entities.CourierPartner

interface CourierPartnersRepo {

    suspend fun getById(id: Int): CourierPartner?

    suspend fun getByIds(ids: List<Int>): List<CourierPartner>
}
