package `in`.porter.cfms.servers.ktor.external.usecases.franchises

import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsRequest
import `in`.porter.cfms.api.service.exceptions.CfmsException
import `in`.porter.cfms.api.service.franchises.usecases.CreateFranchiseRecordService
import `in`.porter.cfms.domain.usecases.external.RecordFranchiseDetails
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class CreateFranchiseRecordHttpService
@Inject
constructor(
    private val service: CreateFranchiseRecordService
) : Traceable {
    companion object : Logging
    suspend fun invoke(call: ApplicationCall) {
        trace {
            try{
                call.receive<RecordFranchiseDetailsRequest>()
                    .also{logger.info{"Request payload for create franchise: $it"}}
                    .let{service.invoke(it)}
                    .let{call.respond(HttpStatusCode.OK,it)}
            } catch(e: CfmsException){
                call.respond(HttpStatusCode.UnprocessableEntity,e)
            }
        }
    }
}
