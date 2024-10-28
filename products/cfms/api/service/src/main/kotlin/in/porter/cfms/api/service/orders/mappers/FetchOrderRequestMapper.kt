package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.FetchOrdersRequest
import javax.inject.Inject
import `in`.porter.cfms.api.models.orders.FetchOrderApiRequest as ApiFetchOrderRequest
import `in`.porter.cfms.domain.orders.entities.FetchOrdersRequest as DomainFetchOrderRequest

class FetchOrderRequestMapper
@Inject constructor() {
    fun fromApi(req: FetchOrdersRequest): DomainFetchOrderRequest {
        return DomainFetchOrderRequest(
            page = req.page,
            size = req.size,
            franchiseId = req.franchiseId,
            orderId = req.orderId,
            awbNumber = req.awbNumber,
            courierPartnerName = req.courierPartnerName,
            senderName = req.senderName,
            senderPhoneNo = req.senderPhoneNo,
            senderPinCode = req.senderPinCode,
            senderCityName = req.senderCityName,
            pickupDate = req.pickupDate,
            isFranchiseUpdated = req.isFranchiseUpdated,
            receiverName = req.receiverName,
            receiverPhoneNo = req.receiverPhoneNo,
            receiverPinCode = req.receiverPinCode,
            receiverCityName = req.receiverCityName,
            hlpOrderId = req.hlpOrderId,
            hlpOrderStatus = req.hlpOrderStatus,
            vehicleType = req.vehicleType,
            riderNumber = req.riderNumber,
            createdDate = req.createdDate,
            updatedDate = req.updatedDate,
            orderStatus = req.orderStatus,
        )
    }
}

