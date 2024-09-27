package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.Order
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import software.amazon.awssdk.core.pagination.async.PaginationSubscription
import javax.inject.Inject

class OrderDetailsMapper @Inject constructor(
    private val basicDetailsMapper: BasicDetailsMapper,
    private val addressDetailsMapper: AddressDetailsMapper,
    private val itemDetailsMapper: ItemDetailsMapper,
    private val shippingDetailsMapper: ShippingDetailsMapper
) {
    fun toDomain(row: ResultRow): Order {
        print("this is query result")
        return Order(
            basicDetails = basicDetailsMapper.fromResultRow(row),
            addressDetails = addressDetailsMapper.fromResultRow(row),
            itemDetails = itemDetailsMapper.fromResultRow(row),
            shippingDetails = shippingDetailsMapper.fromResultRow(row)
        )
    }
    fun mapOrders (query: Query): List<Order> {
        print("this is query result")
        return query
            .map { row: ResultRow -> toDomain(row) }
    }
}