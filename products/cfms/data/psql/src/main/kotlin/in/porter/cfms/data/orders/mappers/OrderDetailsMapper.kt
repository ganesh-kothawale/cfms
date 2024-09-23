package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.Order
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class OrderDetailsMapper @Inject constructor(
    private val basicDetailsMapper: BasicDetailsMapper,
    private val addressDetailsMapper: AddressDetailsMapper,
    private val itemDetailsMapper: ItemDetailsMapper,
    private val shippingDetailsMapper: ShippingDetailsMapper
) {
    fun toDomain(row: ResultRow): Order {
        return Order(
            basicDetails = basicDetailsMapper.fromResultRow(row),
            addressDetails = addressDetailsMapper.fromResultRow(row),
            itemDetails = itemDetailsMapper.fromResultRow(row),
            shippingDetails = shippingDetailsMapper.fromResultRow(row)
        )
    }
}