package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.ItemDetails
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
}