package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.entities.AddressDetails
import `in`.porter.cfms.domain.orders.entities.SenderDetails
import `in`.porter.cfms.domain.orders.entities.ReceiverDetails
import `in`.porter.cfms.domain.orders.entities.ItemDetails
import `in`.porter.cfms.domain.orders.entities.ShippingDetails
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import javax.inject.Inject

class OrderDetailsQueries
@Inject constructor(
    val db: Database,
) {
    suspend fun createOrder(request: CreateOrderRequest) {
        transaction {
            OrdersTable.insert {
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
            }
        }
    }

    suspend fun fetchOrderDetailsByOrderNumber(orderNumber: String): Query? {
        return transaction {
            OrdersTable.select { OrdersTable.awbNumber eq orderNumber }
        }
    }
}