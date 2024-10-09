package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.Location
import javax.inject.Inject
import `in`.porter.cfms.domain.orders.entities.Location as DomainLocation

class LocationMapper
@Inject constructor() {
    fun map(location: Location): DomainLocation {
        return DomainLocation(
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

    fun fromDomain(domainLocation: DomainLocation): Location {
        return Location(
            latitude = domainLocation.latitude,
            longitude = domainLocation.longitude
        )

    }
}