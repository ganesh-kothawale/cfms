package `in`.porter.cfms.api.service.orders.factories

import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import `in`.porter.cfms.domain.orders.entities.BasicDetails
import `in`.porter.cfms.domain.orders.entities.AssociationDetails
import `in`.porter.cfms.domain.orders.entities.AddressDetails
import `in`.porter.cfms.domain.orders.entities.SenderDetails
import `in`.porter.cfms.domain.orders.entities.PersonalInfo
import `in`.porter.cfms.domain.orders.entities.Address
import `in`.porter.cfms.domain.orders.entities.Location
import `in`.porter.cfms.domain.orders.entities.ReceiverDetails
import `in`.porter.cfms.domain.orders.entities.ItemDetails
import `in`.porter.cfms.domain.orders.entities.Dimensions
import `in`.porter.cfms.domain.orders.entities.ShippingDetails
import `in`.porter.cfms.domain.orders.entities.CourierTransportDetails

object CreateOrderRequestFactory {

    fun buildCreateOrderRequest(): CreateOrderRequest {
        return CreateOrderRequest(
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
                materialWeight = 1000,
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