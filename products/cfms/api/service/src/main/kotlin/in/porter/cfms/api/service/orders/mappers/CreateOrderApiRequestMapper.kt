package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.*
import javax.inject.Inject

class CreateOrderApiRequestMapper
@Inject constructor() {

    fun toRequestV1(request: CreateOrderApiRequest): CreateOrderApiRequestV2 {
        val basicDetails = BasicDetails(
            associationDetails = AssociationDetails(
                franchiseId = request.data.franchiseId,
                teamId = request.data.zorpTeamId
            ),
            orderNumber = request.data.orderNumber,
            awbNumber = request.data.awbNumber,
            accountId = request.data.accountId,
            accountCode = request.data.accountCode,
            courierTransportDetails = CourierTransportDetails(
                courierPartnerName = request.data.courierPartnerName,
                modeOfTransport = request.data.modeOfTransport
            ),
            orderStatus = request.data.orderStatus.first()
        )

        val addressDetails = AddressDetails(
            senderDetails = SenderDetails(
                personalInfo = PersonalInfo(
                    name = request.data.senderName,
                    mobileNumber = request.data.senderMobileNumber
                ),
                address = Address(
                    houseNumber = request.data.senderHouseNumber,
                    addressDetails = request.data.senderAddressDetails,
                    cityName = request.data.senderCityName,
                    stateName = request.data.senderStateName,
                    pincode = request.data.senderPincode,
                ),
                location = Location(
                    latitude = request.data.senderLat,
                    longitude = request.data.senderLong
                )


            ),
            receiverDetails = ReceiverDetails(
                personalInfo = PersonalInfo(
                    name = request.data.receiverName,
                    mobileNumber = request.data.receiverMobileNumber
                ),
                address = Address(
                    houseNumber = request.data.receiverHouseNumber,
                    addressDetails = request.data.receiverAddressDetails,
                    cityName = request.data.receiverCityName,
                    stateName = request.data.receiverStateName,
                    pincode = request.data.senderPincode,
                )
            )
        )

        val itemDetails = ItemDetails(
            materialType = request.data.materialType,
            materialWeight = request.data.materialWeight,
            dimensions = Dimensions(
                length = request.data.length,
                breadth = request.data.breadth,
                height = request.data.height
            )
        )

        val shippingDetails = ShippingDetails(
            shippingLabelLink = request.data.shippingLabelLink,
            pickUpDate = request.data.pickUpDate,
            volumetricWeight = request.data.volumetricWeight
        )

        return CreateOrderApiRequestV2(
            basicDetails = basicDetails,
            addressDetails = addressDetails,
            itemDetails = itemDetails,
            shippingDetails = shippingDetails
        )
    }
}