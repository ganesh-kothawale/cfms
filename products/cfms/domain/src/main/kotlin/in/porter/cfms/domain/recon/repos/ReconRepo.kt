package `in`.porter.cfms.domain.recon.repos

import `in`.porter.cfms.domain.recon.entities.Recon

interface ReconRepo {
    suspend fun findAllRecons(page: Int, size: Int): List<Recon>
    suspend fun countAllRecons(): Int
    suspend fun create(recon: Recon): String
}
