package `in`.porter.cfms.api.service.orders.factories

import `in`.porter.cfms.api.models.orders.AddressDetails as ApiAddressDetails
import `in`.porter.cfms.api.models.orders.SenderDetails as ApiSenderDetails
import `in`.porter.cfms.api.models.orders.ReceiverDetails as ApiReceiverDetails
import `in`.porter.cfms.domain.orders.entities.AddressDetails as DomainAddressDetails
import `in`.porter.cfms.domain.orders.entities.SenderDetails as DomainSenderDetails
import `in`.porter.cfms.domain.orders.entities.ReceiverDetails as DomainReceiverDetails
import `in`.porter.cfms.api.models.orders.PersonalInfo as ApiPersonalInfo
import `in`.porter.cfms.api.models.orders.Address as ApiAddress
import `in`.porter.cfms.api.models.orders.Location as ApiLocation
import `in`.porter.cfms.domain.orders.entities.PersonalInfo as DomainPersonalInfo
import `in`.porter.cfms.domain.orders.entities.Address as DomainAddress
import `in`.porter.cfms.domain.orders.entities.Location as DomainLocation

object AddressDetailsFactory {

     fun buildApiPersonalInfo(
        name: String = "John Doe",
        mobileNumber: String = "1234567890"
    ) = ApiPersonalInfo(
        name = name,
        mobileNumber = mobileNumber
    )

     fun buildApiAddress(
        houseNumber: String = "123",
        addressDetails: String = "Street 1",
        cityName: String = "City",
        stateName: String = "State",
        pincode: Int = 123456
    ) = ApiAddress(
        houseNumber = houseNumber,
        addressDetails = addressDetails,
        cityName = cityName,
        stateName = stateName,
        pincode = pincode
    )

     fun buildApiLocation(
        latitude: Double = 12.34,
        longitude: Double = 56.78
    ) = ApiLocation(
        latitude = latitude,
        longitude = longitude
    )

     fun buildApiSenderDetails(
        personalInfo: ApiPersonalInfo = buildApiPersonalInfo(),
        address: ApiAddress = buildApiAddress(),
        location: ApiLocation = buildApiLocation()
    ) = ApiSenderDetails(
        personalInfo = personalInfo,
        address = address,
        location = location
    )

     fun buildApiReceiverDetails(
        personalInfo: ApiPersonalInfo = buildApiPersonalInfo(),
        address: ApiAddress = buildApiAddress()
    ) = ApiReceiverDetails(
        personalInfo = personalInfo,
        address = address
    )

     fun buildApiAddressDetails(
        senderDetails: ApiSenderDetails = buildApiSenderDetails(),
        receiverDetails: ApiReceiverDetails = buildApiReceiverDetails()
    ) = ApiAddressDetails(
        senderDetails = senderDetails,
        receiverDetails = receiverDetails
    )

     fun buildDomainPersonalInfo(
        name: String = "John Doe",
        mobileNumber: String = "1234567890"
    ) = DomainPersonalInfo(
        name = name,
        mobileNumber = mobileNumber
    )

     fun buildDomainAddress(
        houseNumber: String = "123",
        addressDetails: String = "Street 1",
        cityName: String = "City",
        stateName: String = "State",
        pincode: Int = 123456
    ) = DomainAddress(
        houseNumber = houseNumber,
        addressDetails = addressDetails,
        cityName = cityName,
        stateName = stateName,
        pincode = pincode
    )

     fun buildDomainLocation(
        latitude: Double = 12.34,
        longitude: Double = 56.78
    ) = DomainLocation(
        latitude = latitude,
        longitude = longitude
    )

     fun buildDomainSenderDetails(
        personalInfo: DomainPersonalInfo = buildDomainPersonalInfo(),
        address: DomainAddress = buildDomainAddress(),
        location: DomainLocation = buildDomainLocation()
    ) = DomainSenderDetails(
        personalInfo = personalInfo,
        address = address,
        location = location
    )

     fun buildDomainReceiverDetails(
        personalInfo: DomainPersonalInfo = buildDomainPersonalInfo(),
        address: DomainAddress = buildDomainAddress()
    ) = DomainReceiverDetails(
        personalInfo = personalInfo,
        address = address
    )

     fun buildDomainAddressDetails(
        senderDetails: DomainSenderDetails = buildDomainSenderDetails(),
        receiverDetails: DomainReceiverDetails = buildDomainReceiverDetails()
    ) = DomainAddressDetails(
        senderDetails = senderDetails,
        receiverDetails = receiverDetails
    )
}