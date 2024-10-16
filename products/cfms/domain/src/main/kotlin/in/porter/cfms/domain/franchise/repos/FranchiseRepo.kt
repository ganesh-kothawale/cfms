package `in`.porter.cfms.domain.franchise.repos

import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.entities.ListFranchise
import `in`.porter.cfms.domain.franchise.entities.UpdateFranchise

interface FranchiseRepo {

    suspend fun create(franchise: Franchise): Unit
    suspend fun getByCode(franchiseCodes: String): Franchise?
    suspend fun getByEmail(email: String): Franchise?
    suspend fun findAll(page: Int, size: Int): List<ListFranchise>
    suspend fun countAll(): Int
    suspend fun update(request: UpdateFranchise) : Int?
}