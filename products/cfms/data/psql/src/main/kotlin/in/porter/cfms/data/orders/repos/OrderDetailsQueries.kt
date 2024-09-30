package `in`.porter.cfms.data.orders.repos

import arrow.effects.typeclasses.Dispatchers
import `in`.porter.cfms.data.orders.entities.Order
import `in`.porter.cfms.data.orders.mappers.OrderDetailsMapper
import `in`.porter.cfms.domain.orders.entities.*
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import javax.inject.Inject

class OrderDetailsQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    val mapper: OrderDetailsMapper
) : ExposedRepo {

    suspend fun createOrder(request: CreateOrderRequest): Int {
        return transaction {
            OrdersTable.insertAndGetId {
                it[orderNumber] = request.basicDetails.orderNumber
                it[awbNumber] = request.basicDetails.awbNumber
                it[courierPartner] = request.basicDetails.courierTransportDetails.courierPartnerName
                it[modeOfTransport] = request.basicDetails.courierTransportDetails.modeOfTransport
                it[senderName] = request.addressDetails.senderDetails.personalInfo.name
                it[senderMobile] = request.addressDetails.senderDetails.personalInfo.mobileNumber
                it[senderAddress] = request.addressDetails.senderDetails.address.addressDetails
                it[senderCity] = request.addressDetails.senderDetails.address.cityName
                it[senderPincode] = request.addressDetails.senderDetails.address.pincode
                it[senderState] = request.addressDetails.senderDetails.address.stateName
                it[senderLatitude] = request.addressDetails.senderDetails.location.latitude
                it[senderLongitude] = request.addressDetails.senderDetails.location.longitude
                it[receiverName] = request.addressDetails.receiverDetails.personalInfo.name
                it[receiverMobile] = request.addressDetails.receiverDetails.personalInfo.mobileNumber
                it[receiverAddress] = request.addressDetails.receiverDetails.address.addressDetails
                it[receiverPincode] = request.addressDetails.receiverDetails.address.pincode
                it[receiverHomeNumber] = request.addressDetails.receiverDetails.address.houseNumber
                it[receiverCity] = request.addressDetails.receiverDetails.address.cityName
                it[receiverState] = request.addressDetails.receiverDetails.address.stateName
                it[materialType] = request.itemDetails.materialType
                it[materialWeight] = request.itemDetails.materialWeight
                it[dimensionsLength] = request.itemDetails.dimensions?.length
                it[dimensionsBreadth] = request.itemDetails.dimensions?.breadth
                it[dimensionsHeight] = request.itemDetails.dimensions?.height
                it[volumetricWeight] = request.shippingDetails.volumetricWeight
                it[shippingLabelLink] = request.shippingDetails.shippingLabelLink
                it[pickupDate] = request.shippingDetails.pickUpDate
                it[orderStatus] = request.basicDetails.orderStatus
                it[franchiseId] = request.basicDetails.associationDetails.franchiseId
                it[accountId] = request.basicDetails.accountId
                it[accountCode] = request.basicDetails.accountCode
                it[teamId] = request.basicDetails.associationDetails.teamId
                it[createdAt] = Instant.now()
                it[updatedAt] = Instant.now()
            }.value
        }
    }

    suspend fun fetchOrderDetailsByOrderNumber(orderNumber: String): `in`.porter.cfms.data.orders.entities.Order? {
        return transaction {
            OrdersTable.select { OrdersTable.awbNumber eq orderNumber }
            .let { it.mapNotNull { row: ResultRow -> mapper.fromResultRow(row) }?.singleOrNull() }
        }
    }

    suspend fun fetchOrders(limit: Int, offset: Int): List<Order> = transact {
        OrdersTable.selectAll()
            .orderBy(OrdersTable.createdAt, SortOrder.DESC)
            .limit(limit, offset)
            .let  {mapper.mapOrders(it)}
    }

    suspend fun getOrderCount(): Int = transact {
        OrdersTable.selectAll().count()
    }
}
