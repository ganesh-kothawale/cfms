package orders.usecases

import `in`.porter.cfms.api.models.orders.CreateOrderApiRequest
import orders.mappers.CreateOrderApiRequestMapper
import javax.inject.Inject

class CreateOrderService
@Inject
constructor(
//    private val service: CreateOrderService,
    private val apiMapper: CreateOrderApiRequestMapper,
//    private val mapper: RequestToDomainMapper
) {

    suspend fun invoke(request: CreateOrderApiRequest) {

        try {
            apiMapper.toRequestV1(request)
                .let { print(it) }

//                .let { mapper.toDomain(it) }
//                .let { service.createOrder(it) }
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid request")
        } catch (e: Exception) {
            throw Exception("Internal server error")
        }
    }
}