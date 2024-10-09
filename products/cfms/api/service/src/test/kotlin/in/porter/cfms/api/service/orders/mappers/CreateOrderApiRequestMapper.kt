package `in`.porter.cfms.api.service.orders.factories

import `in`.porter.cfms.api.models.orders.*

object CreateOrderApiRequestFactory {

    fun buildCreateOrderApiRequest(
    ): CreateOrderApiRequest {
        return CreateOrderApiRequest(
            tableType = "order",
            data = OrderData(
                franchiseId = "franchiseId",
                zorpTeamId = "teamId",
                orderNumber = "orderNumber",
                awbNumber = "awbNumber",
                accountId = 123,
                accountCode = "accountCode",
                courierPartnerName = "courierPartnerName",
                modeOfTransport = "modeOfTransport",
                orderStatus = listOf("orderStatus"),
                senderName = "senderName",
                senderMobileNumber = "senderMobileNumber",
                senderHouseNumber = "senderHouseNumber",
                senderAddressDetails = "senderAddressDetails",
                senderCityName = "senderCityName",
                senderStateName = "senderStateName",
                senderPincode = 123456,
                senderLat = 12.34,
                senderLong = 56.78,
                receiverName = "receiverName",
                receiverMobileNumber = "receiverMobileNumber",
                receiverHouseNumber = "receiverHouseNumber",
                receiverAddressDetails = "receiverAddressDetails",
                receiverCityName = "receiverCityName",
                receiverStateName = "receiverStateName",
                shippingLabelLink = "http://example.com/label",
                pickUpDate = "2023-10-01",
                materialType = "materialType",
                materialWeight = 1000,
                length = 10.0,
                breadth = 5.0,
                height = 2.0,
                volumetricWeight = 10,
                receiverPincode = 123456

            )
        )
    }

    fun buildCreateOrderApiRequestV1(
    ): CreateOrderApiRequestV2 {
        return CreateOrderApiRequestV2(
            basicDetails = BasicDetails(
                associationDetails = AssociationDetails(
                    franchiseId = "franchiseId",
                    teamId = "teamId"
                ),
                orderNumber = "orderNumber",
                awbNumber = "awbNumber",
                accountId = 123,
                accountCode = "accountCode",
                courierTransportDetails = CourierTransportDetails(
                    courierPartnerName = "courierPartnerName",
                    modeOfTransport = "modeOfTransport"
                ),
                orderStatus = "orderStatus"
            ),
            addressDetails = AddressDetails(
                senderDetails = SenderDetails(
                    personalInfo = PersonalInfo(
                        name = "senderName",
                        mobileNumber = "senderMobileNumber"
                    ),
                    address = Address(
                        houseNumber = "senderHouseNumber",
                        addressDetails = "senderAddressDetails",
                        cityName = "senderCityName",
                        stateName = "senderStateName",
                        pincode = 123456
                    ),
                    location = Location(
                        latitude = 12.34,
                        longitude = 56.78
                    )
                ),
                receiverDetails = ReceiverDetails(
                    personalInfo = PersonalInfo(
                        name = "receiverName",
                        mobileNumber = "receiverMobileNumber"
                    ),
                    address = Address(
                        houseNumber = "receiverHouseNumber",
                        addressDetails = "receiverAddressDetails",
                        cityName = "receiverCityName",
                        stateName = "receiverStateName",
                        pincode = 123456
                    )
                )
            ),
            itemDetails = ItemDetails(
                materialType = "materialType",
                materialWeight = 1000,  // Ensure this matches the expected value
                dimensions = Dimensions(
                    length = 10.0,
                    breadth = 5.0,
                    height = 2.0
                )
            ),
            shippingDetails = ShippingDetails(
                shippingLabelLink = "http://example.com/label",
                pickUpDate = "2023-10-01",
                volumetricWeight = 10
            )
        )
    }
}
