package courierpartner.usecases

import courierpartner.mappers.CreateCourierPartnerRequestMapper
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerResponse
import `in`.porter.cfms.domain.courierPartner.usecases.internal.CreateCourierPartner
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable

import javax.inject.Inject

class CreateCourierPartnerService
@Inject
constructor(
    private val mapper: CreateCourierPartnerRequestMapper,
    private val createCourierPartner: CreateCourierPartner
) : Traceable {

    suspend fun invoke(req: CreateCourierPartnerApiRequest): CreateCourierPartnerResponse = trace {
        try {
            mapper.toDomain(req)
                .let { createCourierPartner.invoke(it) }
                .let {
                    CreateCourierPartnerResponse("Courier partner added successfully")
                }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}