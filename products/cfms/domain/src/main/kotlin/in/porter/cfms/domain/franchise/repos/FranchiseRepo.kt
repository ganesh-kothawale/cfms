package `in`.porter.cfms.domain.franchise.repos

import `in`.porter.cfms.domain.franchise.entities.DomainListFranchisesRequest
import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.entities.ListFranchise
import `in`.porter.cfms.domain.franchise.entities.UpdateFranchise

interface FranchiseRepo {

    suspend fun create(franchise: Franchise): String
    suspend fun getByCode(franchiseCodes: String): Franchise?
    suspend fun getByEmail(email: String): Franchise?
    suspend fun findAll(request: DomainListFranchisesRequest): List<ListFranchise>
    suspend fun countAll(request: DomainListFranchisesRequest): Int
    suspend fun update(request: UpdateFranchise) : Int?
}