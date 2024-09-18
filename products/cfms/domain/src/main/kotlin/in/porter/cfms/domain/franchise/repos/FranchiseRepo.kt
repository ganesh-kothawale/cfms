package `in`.porter.cfms.domain.franchise.repos

import `in`.porter.cfms.domain.franchise.entities.Franchise

interface FranchiseRepo {

    suspend fun create(franchise: Franchise): Int
    suspend fun getByCode(franchiseCodes: String): Franchise?
}