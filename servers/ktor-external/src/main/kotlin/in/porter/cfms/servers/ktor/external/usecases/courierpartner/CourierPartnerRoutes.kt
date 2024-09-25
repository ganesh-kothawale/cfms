import `in`.porter.cfms.servers.ktor.external.di.HttpComponent
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.core.appender.routing.Routes


fun Route.courierPartnerRoutes(httpComponent: HttpComponent) {
    post("create_record") { httpComponent.createCourierPartnerHttpService.invoke(call) }
}
