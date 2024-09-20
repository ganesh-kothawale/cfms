package orders.mappers

import `in`.porter.cfms.api.models.orders.Location
import `in`.porter.cfms.domain.orders.entities.Location as DomainLocation

class LocationMapper {
    fun map(location: Location): DomainLocation {
        return DomainLocation(
            latitude = location.latitude,
            longitude = location.longitude
        )
    }
}