package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.data.orders.entities.Order
import `in`.porter.cfms.data.orders.mappers.OrderDetailsMapper
import `in`.porter.cfms.domain.orders.entities.*
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.time.Instant
import java.time.ZoneOffset
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
                it[orderId] = request.basicDetails.orderId
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

    suspend fun fetchOrders(request: FetchOrdersRequest, offset: Int): List<Order> = transact {
        addLogger(StdOutSqlLogger)

        OrdersTable.selectAll().apply {

            request.franchiseId?.let { ids ->
                andWhere {
                    ids.map { id -> OrdersTable.franchiseId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.createdDate?.let {
                andWhere { OrdersTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }
            request.updatedDate?.let {
                andWhere { OrdersTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }

            request.orderStatus?.let { andWhere { OrdersTable.orderStatus eq it } }

            // Filter by order IDs with `or` condition for partial matching
            request.orderId?.let { ids ->
                andWhere {
                    ids.map { id -> OrdersTable.orderId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            // Filter by AWB numbers with `or` condition for partial matching
            request.awbNumber?.let { numbers ->
                andWhere {
                    numbers.map { number -> OrdersTable.awbNumber like "%$number%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            // Filter by courier partner names with `or` condition for partial matching
            request.courierPartnerName?.let { partners ->
                andWhere {
                    partners.map { partner -> OrdersTable.courierPartner like "%$partner%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            // Filter by sender details
            request.senderName?.let { names ->
                andWhere {
                    names.map { name -> OrdersTable.senderName like "%$name%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.senderPhoneNo?.let { phones ->
                andWhere {
                    phones.map { phone -> OrdersTable.senderMobile like "%$phone%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.senderCityName?.let { cities ->
                andWhere {
                    cities.map { city -> OrdersTable.senderCity like "%$city%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.senderPinCode?.let { andWhere { OrdersTable.senderPincode eq it } }

            // Filter by pickup date
            request.pickupDate?.let {
                andWhere { OrdersTable.pickupDate eq it }
            }

            request.isFranchiseUpdated?.let { andWhere { OrdersTable.isFranchiseUpdated eq it } }

            // Filter by receiver details
            request.receiverName?.let { names ->
                andWhere {
                    names.map { name -> OrdersTable.receiverName like "%$name%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.receiverPhoneNo?.let { phones ->
                andWhere {
                    phones.map { phone -> OrdersTable.receiverMobile like "%$phone%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.receiverCityName?.let { cities ->
                andWhere {
                    cities.map { city -> OrdersTable.receiverCity like "%$city%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.receiverPinCode?.let { andWhere { OrdersTable.receiverPincode eq it } }

            // Filter by HLP order details
            request.hlpOrderId?.let { ids ->
                andWhere {
                    ids.map { id -> OrdersTable.hlpOrderId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.hlpOrderStatus?.let { andWhere { OrdersTable.hlpOrderStatus eq it } }
            request.vehicleType?.let { andWhere { OrdersTable.vehicleType eq it } }

        }.orderBy(OrdersTable.createdAt, SortOrder.DESC)
            .limit(request.size, offset)
            .let {
                mapper.mapOrders(it)
            }
    }

    // Example count method
    suspend fun getOrderCount(request: FetchOrdersRequest): Int = transact {
        addLogger(StdOutSqlLogger)
        OrdersTable.selectAll().apply {

            request.franchiseId?.let { ids ->
                andWhere {
                    ids.map { id -> OrdersTable.franchiseId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            request.createdDate?.let {
                andWhere { OrdersTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }
            request.updatedDate?.let {
                andWhere { OrdersTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }

            request.orderStatus?.let { andWhere { OrdersTable.orderStatus eq it } }

            // Filter by order IDs with `or` condition for partial matching
            request.orderId?.let { ids ->
                andWhere {
                    ids.map { id -> OrdersTable.orderId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            // Filter by AWB numbers with `or` condition for partial matching
            request.awbNumber?.let { numbers ->
                andWhere {
                    numbers.map { number -> OrdersTable.awbNumber like "%$number%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            // Filter by courier partner names with `or` condition for partial matching
            request.courierPartnerName?.let { partners ->
                andWhere {
                    partners.map { partner -> OrdersTable.courierPartner like "%$partner%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }

            // Filter by sender details
            request.senderName?.let { names ->
                andWhere {
                    names.map { name -> OrdersTable.senderName like "%$name%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.senderPhoneNo?.let { phones ->
                andWhere {
                    phones.map { phone -> OrdersTable.senderMobile like "%$phone%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.senderCityName?.let { cities ->
                andWhere {
                    cities.map { city -> OrdersTable.senderCity like "%$city%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.senderPinCode?.let { andWhere { OrdersTable.senderPincode eq it } }

            // Filter by pickup date
            request.pickupDate?.let {
                andWhere { OrdersTable.pickupDate eq it }
            }

            request.isFranchiseUpdated?.let { andWhere { OrdersTable.isFranchiseUpdated eq it } }

            // Filter by receiver details
            request.receiverName?.let { names ->
                andWhere {
                    names.map { name -> OrdersTable.receiverName like "%$name%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.receiverPhoneNo?.let { phones ->
                andWhere {
                    phones.map { phone -> OrdersTable.receiverMobile like "%$phone%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.receiverCityName?.let { cities ->
                andWhere {
                    cities.map { city -> OrdersTable.receiverCity like "%$city%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.receiverPinCode?.let { andWhere { OrdersTable.receiverPincode eq it } }

            // Filter by HLP order details
            request.hlpOrderId?.let { ids ->
                andWhere {
                    ids.map { id -> OrdersTable.hlpOrderId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.hlpOrderStatus?.let { andWhere { OrdersTable.hlpOrderStatus eq it } }
            request.vehicleType?.let { andWhere { OrdersTable.vehicleType eq it } }

        }.count()
    }

    suspend fun updateStatus(orderId: Int, status: String): Int = transaction {
        OrdersTable.update({ OrdersTable.id eq orderId }) {
            it[orderStatus] = status
        }
        orderId
    }

    suspend fun fetchOrderDetailsByOrderId(orderIds: List<String>): List<`in`.porter.cfms.data.orders.entities.Order>? {
        return transaction {
            OrdersTable.select { OrdersTable.orderId inList orderIds }
                .mapNotNull { row -> mapper.fromResultRow(row) }
        }
    }
}