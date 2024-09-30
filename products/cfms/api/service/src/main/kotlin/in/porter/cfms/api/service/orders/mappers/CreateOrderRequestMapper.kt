package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.CourierOrderResponse
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequestV1
import `in`.porter.cfms.api.models.orders.OrderDetails
import `in`.porter.cfms.domain.orders.entities.CreateOrderRequest
import javax.inject.Inject

class CreateOrderRequestMapper
@Inject constructor(
    private val basicDetailsMapper: BasicDetailsMapper,
    private val addressDetailsMapper: AddressDetailsMapper,
    private val itemDetailsMapper: ItemDetailsMapper,
    private val shippingDetailsMapper: ShippingDetailsMapper
) {
    fun toDomain(requestV1: CreateOrderApiRequestV1): CreateOrderRequest {
        return CreateOrderRequest(
            basicDetails = basicDetailsMapper.map(requestV1.basicDetails),
            addressDetails = addressDetailsMapper.map(requestV1.addressDetails),
            itemDetails = itemDetailsMapper.map(requestV1.itemDetails),
            shippingDetails = shippingDetailsMapper.map(requestV1.shippingDetails)
        )

    }

    suspend fun getResponseById(recordId: Int): CourierOrderResponse {
        val idString = recordId.toString()
        val orderDetails = OrderDetails(
            recordId = idString
        )
        return CourierOrderResponse(
            code = "200",
            message = "order saved successfully",
            data = orderDetails
        )
    }

}