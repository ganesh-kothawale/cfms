package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.Dimensions
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class DimensionsMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): Dimensions? {
        return if (row[OrdersTable.dimensionsLength] != null) {
            Dimensions(
                length = row[OrdersTable.dimensionsLength],
                breadth = row[OrdersTable.dimensionsBreadth],
                height = row[OrdersTable.dimensionsHeight]
            )
        } else {
            null
        }
    }
}