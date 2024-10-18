package `in`.porter.cfms.data.hlp.repos

import `in`.porter.cfms.data.hlp.HlpQueries
import `in`.porter.cfms.data.hlp.mappers.HlpRecordMapper
import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft
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
            .also { println("[PsqlHlpsRepo]") }
            .let { queries.save(it) }
    }
}
