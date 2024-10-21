package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.orders.CourierOrderResponse
import `in`.porter.cfms.api.models.orders.UpdateOrderStatusApiRequest
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderRequestMapper
import `in`.porter.cfms.api.service.orders.mappers.UpdateOrderStatusApiRequestMapper
import `in`.porter.cfms.domain.orders.usecases.UpdateOrderStatusService
import javax.inject.Inject

class UpdateOrderStatusApiService @Inject constructor(
    private val service: UpdateOrderStatusService,
    private val mapper: UpdateOrderStatusApiRequestMapper,
    val responseMapper: CreateOrderRequestMapper,
    private val createAuditLogService: CreateAuditLogService
) {

    suspend fun invoke(request: UpdateOrderStatusApiRequest, orderId: String?): CourierOrderResponse {
        return try {
            val updatedOrderId = mapper.toDomain(request, orderId)
                .let { service.invoke(it) }

            // Map the updated order to a response
            val response = responseMapper.getResponseById(updatedOrderId)

            // After successfully updating the order status, log the audit
            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = updatedOrderId.toString(),
                    entityType = "Order",
                    status = request.orderStatus.toString(),  // Log the new status from the request
                    message = "Order status updated to ${request.orderStatus}",
                    // TODO: Replace hardcoded user ID with actual user ID when available
                    updatedBy = 123  // Hardcoded for now
                )
            )

            // Return the updated order response
            return response

        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid request: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Internal server error: ${e.message}")
        }
    }
}