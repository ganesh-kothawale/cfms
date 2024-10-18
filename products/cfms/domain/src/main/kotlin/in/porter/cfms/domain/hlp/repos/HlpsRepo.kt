package `in`.porter.cfms.domain.hlp.repos

import `in`.porter.cfms.domain.hlp.entities.HlpDetailsDraft

interface HlpsRepo {

    suspend fun create(draft: HlpDetailsDraft): Unit
}
