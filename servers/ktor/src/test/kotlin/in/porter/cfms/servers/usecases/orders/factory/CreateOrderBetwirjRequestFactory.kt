//package `in`.porter.cfms.api.factories
//
//import com.google.api.client.json.Json
//import `in`.porter.cfms.api.models.orders.CreateOrderApiRequest
//import `in`.porter.cfms.api.models.orders.OrderData
//import io.ktor.http.ContentType.Application.Json
//
//object CreateOrderRequestFactory {
//    fun buildCreateOrderApiRequest(): CreateOrderApiRequest {
//        return CreateOrderApiRequest(
//            tableType = "orders",
//            data = OrderData(
//                franchiseId = "exampleFranchiseId",
//                orderNumber = "exampleOrderNumber",
//                awbNumber = "exampleAwbNumber",
//                courierPartnerName = "exampleCourierPartnerName",
//                accountId = 123,
//                accountCode = "exampleAccountCode",
//                modeOfTransport = "exampleModeOfTransport",
//                senderName = "exampleSenderName",
//                senderMobileNumber = "1234567890",
//                senderHouseNumber = "123",
//                senderAddressDetails = "exampleSenderAddressDetails",
//                senderCityName = "exampleSenderCityName",
//                senderStateName = "exampleSenderStateName",
//                senderPincode = 123456,
//                receiverPincode = 654321,
//                senderLat = 12.34,
//                senderLong = 56.78,
//                receiverName = "exampleReceiverName",
//                receiverMobileNumber = "0987654321",
//                receiverHouseNumber = "321",
//                receiverAddressDetails = "exampleReceiverAddressDetails",
//                receiverCityName = "exampleReceiverCityName",
//                receiverStateName = "exampleReceiverStateName",
//                shippingLabelLink = "exampleShippingLabelLink",
//                pickUpDate = "2023-10-01",
//                materialType = "exampleMaterialType",
//                materialWeight = 10,
//                length = 1.0,
//                breadth = 2.0,
//                height = 3.0,
//                volumetricWeight = 4,
//                orderStatus = listOf("exampleStatus1", "exampleStatus2"),
//                zorpTeamId = "exampleZorpTeamId"
//            )
//        )
//    }
//
//    fun buildCreateOrderApiRequestJson(): String {
//        val request = buildCreateOrderApiRequest()
//        return Json.encodeToString(request)
//    }
//}