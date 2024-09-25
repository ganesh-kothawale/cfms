package `in`.porter.cfms.api.service.orders.usecases

import `in`.porter.cfms.api.models.orders.FetchOrderApiRequest
import javax.inject.Inject

class FetchOrdersApiService @Inject constructor() {

    suspend fun invoke(request: FetchOrderApiRequest) {
        // Print the request for now
        println("Received request: $request")

        // Here you would call the domain service
        // domainService.fetchOrders(request)
    }
}