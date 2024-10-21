package `in`.porter.cfms.domain.hlp.usecases

import `in`.porter.cfms.domain.hlp.entities.FetchHlpRecordsRequest
import `in`.porter.cfms.domain.hlp.entities.FetchHlpRecordsResponse
import `in`.porter.cfms.domain.hlp.repos.HlpsRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class FetchHlpRecords
@Inject
constructor(
    private val hlpsRepo: HlpsRepo,
) : Traceable {

    suspend fun invoke(req: FetchHlpRecordsRequest): FetchHlpRecordsResponse = trace {
        val hlpRecords = hlpsRepo.findAll(req)
        val totalRecords = hlpsRepo.countAll()

        FetchHlpRecordsResponse(
            hlps = hlpRecords,
            page = req.page,
            size = req.size,
            totalPages = calculateTotalPages(totalRecords, req.size),
            totalRecords = totalRecords,
        )
    }

    private fun calculateTotalPages(totalRecords: Int, pageSize: Int): Int {
        return if (totalRecords == 0) 0 else (totalRecords + pageSize - 1) / pageSize
    }
}
