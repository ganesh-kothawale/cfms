package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.*
import javax.inject.Inject

class CreateOrderApiRequestMapper
@Inject constructor() {

    fun toRequestV1(request: CreateOrderApiRequest): CreateOrderApiRequestV1 {
        val basicDetails = BasicDetails(
            associationDetails = AssociationDetails(
                franchiseId = request.date.franchiseId,
                teamId = request.date.zorpTeamId
            ),
            orderNumber = request.date.orderNumber,
            awbNumber = request.date.awbNumber,
            accountId = request.date.accountId,
            accountCode = request.date.accountCode,
            courierTransportDetails = CourierTransportDetails(
                courierPartnerName = request.date.courierPartnerName,
                modeOfTransport = request.date.modeOfTransport
            ),
            orderStatus = request.date.orderStatus.first()
        )

        val addressDetails = AddressDetails(
            senderDetails = SenderDetails(
                personalInfo = PersonalInfo(
                    name = request.date.senderName,
                    mobileNumber = request.date.senderMobileNumber
                ),
                address = Address(
                    houseNumber = request.date.senderHouseNumber,
                    addressDetails = request.date.senderAddressDetails,
                    cityName = request.date.senderCityName,
                    stateName = request.date.senderStateName,
                    pincode = request.date.senderPincode,
                ),
                location = Location(
                    latitude = request.date.senderLat,
                    longitude = request.date.senderLong
                )


            ),
            receiverDetails = ReceiverDetails(
                personalInfo = PersonalInfo(
                    name = request.date.receiverName,
                    mobileNumber = request.date.receiverMobileNumber
                ),
                address = Address(
                    houseNumber = request.date.receiverHouseNumber,
                    addressDetails = request.date.receiverAddressDetails,
                    cityName = request.date.receiverCityName,
                    stateName = request.date.receiverStateName,
                    pincode = request.date.senderPincode,
<<<<<<< HEAD
                )
=======
                ),
>>>>>>> parent of 57b6bb7 (Revert "api later test cases done")
            )
        )

        val itemDetails = ItemDetails(
            materialType = request.date.materialType,
            materialWeight = request.date.materialWeight,
            dimensions = Dimensions(
                length = request.date.length,
                breadth = request.date.breadth,
                height = request.date.height
            )
        )

        val shippingDetails = ShippingDetails(
            shippingLabelLink = request.date.shippingLabelLink,
            pickUpDate = request.date.pickUpDate,
            volumetricWeight = request.date.volumetricWeight
        )

        return CreateOrderApiRequestV1(
            basicDetails = basicDetails,
            addressDetails = addressDetails,
            itemDetails = itemDetails,
            shippingDetails = shippingDetails
        )
    }
}