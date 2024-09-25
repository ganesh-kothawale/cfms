package courierpartner.usecases

import courierpartner.mappers.CreateCourierPartnerRequestMapper
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerResponse
import javax.inject.Inject

class CreateCourierPartnerService
@Inject
constructor(
    private val mapper: CreateCourierPartnerRequestMapper,
) : Traceable {

    suspend fun invoke(req: CreateCourierPartnerApiRequest): CreateCourierPartnerResponse = trace {
        try {
            mapper.toDomain(req)
                .let {
                    CreateCourierPartnerResponse("Courier partner added")
                }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}