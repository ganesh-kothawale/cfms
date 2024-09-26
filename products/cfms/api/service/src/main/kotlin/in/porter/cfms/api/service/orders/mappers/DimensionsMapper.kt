package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.Dimensions
import javax.inject.Inject
import `in`.porter.cfms.domain.orders.entities.Dimensions as DomainDimensions

class DimensionsMapper
@Inject constructor() {
    fun map(dimensions: Dimensions): DomainDimensions {
        return DomainDimensions(
            length = dimensions.length,
            breadth = dimensions.breadth,
            height = dimensions.height
        )
    }

    companion object {
        fun fromDomain(domainDimensions: DomainDimensions): Dimensions {
            return Dimensions(
                length = domainDimensions.length,
                breadth = domainDimensions.breadth,
                height = domainDimensions.height
            )
        }
    }
}