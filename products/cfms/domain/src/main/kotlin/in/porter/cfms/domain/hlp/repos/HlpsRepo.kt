package `in`.porter.cfms.domain.hlp.repos

import `in`.porter.cfms.domain.franchise.entities.ListFranchise
import `in`.porter.cfms.domain.hlp.entities.FetchHlpRecordsRequest
import `in`.porter.cfms.domain.hlp.entities.HlpDetails
import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft
import `in`.porter.cfms.domain.hlp.entities.UpdateHlpDetailsRequest

interface HlpsRepo {

    suspend fun create(draft: HlpDetailsDraft): Unit

    suspend fun update(req: UpdateHlpDetailsRequest): Int

    suspend fun getByHlpOrderId(hlpOrderId: String): HlpDetails?

    suspend fun countAll(req: FetchHlpRecordsRequest): Int

    suspend fun findAll(req: FetchHlpRecordsRequest): List<HlpDetails>
}
