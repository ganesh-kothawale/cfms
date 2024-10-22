package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.auditlogs.CreateAuditLogRequest
import `in`.porter.cfms.api.models.orders.CourierOrderResponse
import `in`.porter.cfms.api.models.orders.CreateOrderApiRequestV2
import `in`.porter.cfms.api.service.auditlogs.usecases.CreateAuditLogService
import `in`.porter.cfms.api.service.orders.mappers.CreateOrderRequestMapper
import `in`.porter.cfms.domain.orders.usecases.CreateOrder
import javax.inject.Inject

class CreateOrderService
@Inject
constructor(
    private val service: CreateOrder,
    private val mapper: CreateOrderRequestMapper,
    private val createAuditLogService: CreateAuditLogService
) {

    suspend fun invoke(request: CreateOrderApiRequestV2): CourierOrderResponse {
        try {
            val orderId = mapper.toDomain(request)
                .let { service.invoke(it) }

            // Map the created order to a response
            val response = mapper.getResponseById(orderId)

            // After successful creation, log the audit
            createAuditLogService.createAuditLog(
                CreateAuditLogRequest(
                    entityId = orderId.toString(),
                    entityType = "Order",
                    status = "Created",
                    message = "Order created successfully",
                    // TODO: Replace hardcoded user ID with actual user ID when available
                    updatedBy = 123  // Hardcoded for now
                )
            )

            // Return the created order response
            return response
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid request")
        } catch (e: Exception) {
            throw Exception("Internal server error")
        }
    }
}