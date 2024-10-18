package `in`.porter.cfms.data.hlp.repos

import `in`.porter.cfms.data.exceptions.CfmsException
import `in`.porter.cfms.data.hlp.HlpQueries
import `in`.porter.cfms.data.hlp.mappers.HlpRecordMapper
import `in`.porter.cfms.domain.hlp.entities.FetchHlpRecordsRequest
import `in`.porter.cfms.domain.hlp.entities.HlpDetails
import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft
import `in`.porter.cfms.domain.hlp.entities.UpdateHlpDetailsRequest
import `in`.porter.cfms.domain.hlp.repos.HlpsRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class PsqlHlpsRepo
@Inject
constructor(
    private val queries: HlpQueries,
    private val mapper: HlpRecordMapper
) : Traceable, HlpsRepo {
    override suspend fun create(draft: HlpDetailsDraft) {
        mapper.toData(draft)
            .let { queries.save(it) }
    }

    override suspend fun update(req: UpdateHlpDetailsRequest) {
        mapper.toUpdateHlpRecord(req)
            .let { queries.update(it) }
    }

    override suspend fun countAll(): Int =
        trace("countAll") {
            queries.countAll()
        }

    override suspend fun findAll(req: FetchHlpRecordsRequest): List<HlpDetails> =
        trace("findAll") {
            val offset = (req.page - 1) * req.size

            queries.findAll(req.size, offset)
                .map { mapper.fromRecord(it) }
        }

}
