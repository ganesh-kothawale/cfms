package `in`.porter.cfms.servers.ktor.usecases.orders

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.orders.FetchOrdersRequest
import `in`.porter.cfms.servers.ktor.di.HttpComponent
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime


fun Route.publicOrdersRoutes(httpComponent: HttpComponent) {
    get("/orders") {
        try {
            val request = FetchOrdersRequest(
                page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1,
                size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10,
                franchiseId = call.request.queryParameters["franchise_id"]?.split(",")
                    ?.map { it.trim() },
                createdDate = call.request.queryParameters["created_date"]?.let { LocalDateTime.parse(it) },
                updatedDate = call.request.queryParameters["updated_date"]?.let { LocalDateTime.parse(it) },
                orderStatus = call.request.queryParameters["order_status"],
                orderId = call.request.queryParameters["order_id"]?.split(",")?.map { it.trim() },
                awbNumber = call.request.queryParameters["awb_number"]?.split(",")
                    ?.map { it.trim() },
                courierPartnerName = call.request.queryParameters["courier_partner_name"]?.split(",")
                    ?.map { it.trim() },
                senderName = call.request.queryParameters["sender_name"]?.split(",")
                    ?.map { it.trim() },
                senderPhoneNo = call.request.queryParameters["sender_phone_no"]?.split(",")
                    ?.map { it.trim() },
                senderCityName = call.request.queryParameters["sender_city_name"]?.split(",")
                    ?.map { it.trim() },
                senderPinCode = call.request.queryParameters["sender_pin_code"],
                pickupDate = call.request.queryParameters["pickup_date"]?.let { LocalDateTime.parse(it) },
                isFranchiseUpdated = call.request.queryParameters["is_franchise_updated"]?.toBoolean(),
                receiverName = call.request.queryParameters["receiver_name"]?.split(",")
                    ?.map { it.trim() },
                receiverPhoneNo = call.request.queryParameters["receiver_phone_no"]?.split(",")
                    ?.map { it.trim() },
                receiverCityName = call.request.queryParameters["receiver_city_name"]?.split(",")
                    ?.map { it.trim() },
                receiverPinCode = call.request.queryParameters["receiver_pin_code"],
                hlpOrderId = call.request.queryParameters["hlp_order_id"]?.split(",")
                    ?.map { it.trim() },
                hlpOrderStatus = call.request.queryParameters["hlp_order_status"],
                vehicleType = call.request.queryParameters["vehicle_type"],
                riderNumber = call.request.queryParameters["rider_number"]
            )

            val orders = httpComponent.fetchOrdersHTTPService.invoke(call, request)
            call.respond(orders)
        } catch (e: CfmsException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(
                    "error" to listOf(
                        mapOf(
                            "message" to "Invalid query parameters",
                            "details" to e.message
                        )
                    )
                )
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "An unexpected error occurred")
            )
        }
    }
}

fun Route.privateOrdersRoutes(httpComponent: HttpComponent) {
    post("") { httpComponent.createOrderHTTPService.invoke(call) }
    patch("") { httpComponent.updateOrderStatusHTTPService.invoke(call) }
}