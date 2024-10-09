package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.orders.entities.ItemDetails
import `in`.porter.cfms.domain.orders.entities.ItemDetails as DomainItemDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class ItemDetailsMapper @Inject constructor(
    private val dimensionsMapper: DimensionsMapper
) {
    fun fromResultRow(row: ResultRow): ItemDetails {
        return ItemDetails(
            materialType = row[OrdersTable.materialType],
            materialWeight = row[OrdersTable.materialWeight],
            dimensions = dimensionsMapper.fromResultRow(row)
        )
    }

    fun toDomain(entity: ItemDetails): DomainItemDetails {
        return DomainItemDetails(
            materialType = entity.materialType,
            materialWeight = entity.materialWeight,
            dimensions = entity.dimensions?.let { dimensionsMapper.toDomain(it) }
        )
    }
}