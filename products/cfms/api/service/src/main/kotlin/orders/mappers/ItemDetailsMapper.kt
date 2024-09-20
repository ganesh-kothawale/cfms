package orders.mappers

import `in`.porter.cfms.api.models.orders.ItemDetails
import `in`.porter.cfms.domain.orders.entities.ItemDetails as DomainItemDetails
import javax.inject.Inject

class ItemDetailsMapper
@Inject constructor(
    private val dimensionsMapper: DimensionsMapper
) {
    fun map(itemDetails: ItemDetails): DomainItemDetails {
        return DomainItemDetails(
            materialType = itemDetails.materialType,
            materialWeight = itemDetails.materialWeight,
            dimensions = itemDetails.dimensions?.let { dimensionsMapper.map(it) }
        )
    }
}