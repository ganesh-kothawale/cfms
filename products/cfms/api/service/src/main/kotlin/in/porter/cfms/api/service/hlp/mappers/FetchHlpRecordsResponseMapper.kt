package `in`.porter.cfms.api.service.hlp.mappers

import `in`.porter.cfms.domain.hlp.entities.FetchHlpRecordsResponse
import `in`.porter.cfms.api.models.hlp.FetchHlpRecordsResponse as FetchHlpRecordsResponseApi
import javax.inject.Inject

class FetchHlpRecordsResponseMapper
@Inject
constructor(
    private val hlpDetailsMapper: HlpDetailsMapper,
) {

    fun fromDomain(req: FetchHlpRecordsResponse) = FetchHlpRecordsResponseApi.Success(
        hlps = req.hlps.map { hlpDetailsMapper.fromDomain(it) },
        page = req.page,
        size = req.size,
        totalPages = req.totalPages,
        totalRecords = req.totalRecords
    )

}
