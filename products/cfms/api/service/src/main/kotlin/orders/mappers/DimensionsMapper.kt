package orders.mappers

import `in`.porter.cfms.api.models.orders.Dimensions
import `in`.porter.cfms.domain.orders.entities.Dimensions as DomainDimensions

class DimensionsMapper {
    fun map(dimensions: Dimensions): DomainDimensions {
        return DomainDimensions(
            length = dimensions.length,
            breadth = dimensions.breadth,
            height = dimensions.height
        )
    }
}