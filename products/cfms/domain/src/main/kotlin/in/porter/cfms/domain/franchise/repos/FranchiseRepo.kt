package `in`.porter.cfms.domain.franchise.repos

import `in`.porter.cfms.domain.franchise.entities.Franchise

interface FranchiseRepo {

    suspend fun create(franchise: Franchise): Unit
    suspend fun getByCode(franchiseCodes: String): Franchise?
    suspend fun getByEmail(email: String): Franchise?
}