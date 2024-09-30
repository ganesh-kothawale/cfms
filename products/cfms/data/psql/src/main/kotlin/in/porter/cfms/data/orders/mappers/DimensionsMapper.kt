package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.Dimensions
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.domain.orders.entities.Dimensions as DomainDimensions
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class DimensionsMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): Dimensions? {
        return if (row[OrdersTable.dimensionsLength] != null) {
            Dimensions(
                length = row[OrdersTable.dimensionsLength] ?: 0.0,
                breadth = row[OrdersTable.dimensionsBreadth] ?: 0.0,
                height = row[OrdersTable.dimensionsHeight] ?: 0.0
            )
        } else {
            null
        }
    }

    fun toDomain(entity: Dimensions): DomainDimensions {
        return DomainDimensions(
            length = entity.length,
            breadth = entity.breadth,
            height = entity.height
        )
    }
}