package `in`.porter.cfms.domain.franchise.repos

import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.entities.ListFranchise

interface FranchiseRepo {

    suspend fun create(franchise: Franchise): Unit
    suspend fun getByCode(franchiseCodes: String): Franchise?
    suspend fun getByEmail(email: String): Franchise?
    suspend fun findAll(page: Int, size: Int): List<ListFranchise>
    suspend fun countAll(): Int
}