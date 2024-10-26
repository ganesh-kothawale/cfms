package `in`.porter.cfms.api.service.courierpartner.mappers

import `in`.porter.cfms.api.models.cpConnections.CPConnection as CPConnectionApi
import `in`.porter.cfms.domain.cpConnections.entities.CPConnection
import `in`.porter.cfms.domain.courierPartners.entities.CourierPartner
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class CPConnectionMapper
@Inject
constructor() {

    fun fromDomain(cpConnection: CPConnection, cp: CourierPartner) = CPConnectionApi(
        id = cpConnection.id,
        cpId = cpConnection.cpId,
        franchiseId = cpConnection.franchiseId,
        manifestImageUrl = cpConnection.manifestImageUrl,
        courierPartnerName = cp.name,
        createdAt = DateTimeFormatter.ISO_INSTANT.format(cpConnection.createdAt.truncatedTo(ChronoUnit.SECONDS)),
    )

}
