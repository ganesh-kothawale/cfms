
package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.entities.Order
import org.jetbrains.exposed.sql.Query
import `in`.porter.cfms.domain.orders.entities.Order as DomainOrder
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class OrderDetailsMapper @Inject constructor(
    private val basicDetailsMapper: BasicDetailsMapper,
    private val addressDetailsMapper: AddressDetailsMapper,
    private val itemDetailsMapper: ItemDetailsMapper,
    private val shippingDetailsMapper: ShippingDetailsMapper
) {
    fun fromResultRow(row: ResultRow): Order {
        return Order(
            basicDetails = basicDetailsMapper.fromResultRow(row),
            addressDetails = addressDetailsMapper.fromResultRow(row),
            itemDetails = itemDetailsMapper.fromResultRow(row),
            shippingDetails = shippingDetailsMapper.fromResultRow(row)
        )
    }

    fun toDomain(entity: Order): DomainOrder {
        return DomainOrder(
            basicDetails = basicDetailsMapper.toDomain(entity.basicDetails),
            addressDetails = addressDetailsMapper.toDomain(entity.addressDetails),
            itemDetails = itemDetailsMapper.toDomain(entity.itemDetails),
            shippingDetails = shippingDetailsMapper.toDomain(entity.shippingDetails)
        )
    }
fun mapOrders (query: Query): List<Order> {
        return query
            .map { row: ResultRow -> fromResultRow(row) }
    }
}