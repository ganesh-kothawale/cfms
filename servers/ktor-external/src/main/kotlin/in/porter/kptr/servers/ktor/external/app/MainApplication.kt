package `in`.porter.kptr.servers.ktor.external.app

import `in`.porter.kotlinutils.instrumentation.sentryktorfeature.SentryKtorFeature
import `in`.porter.kotlinutils.serde.jackson.custom.*
import `in`.porter.kotlinutils.webserver.ktor.features.calltracing.CallTracingFeature
import `in`.porter.kotlinutils.webserver.ktor.features.country.CountryServerFeature
import `in`.porter.kotlinutils.webserver.ktor.features.timeout.TimeoutFeature
import `in`.porter.kotlinutils.webserver.ktor.features.tracingids.requestId
import `in`.porter.kotlinutils.webserver.ktor.features.tracingids.traceId
import `in`.porter.kptr.servers.ktor.external.di.HttpComponentFactory
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.util.*
import java.time.Duration

fun Application.main() {

    install(ContentNegotiation) {
        jackson {
            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
            registerModules(
                KotlinModule.Builder().build(),
                InstantSerde.millisModule,
                LocalDateSerde.localDateModule,
                LocalTimeSerde.localTimeSerde,
                DurationSerde.millisModule,
                MoneySerde.moneyModule,
                UrlSerde.urlModule
            )
        }
    }

    install(XForwardedHeaders)

    install(DoubleReceive)

    install(CallTracingFeature)

    install(CountryServerFeature)

    install(TimeoutFeature) {
        duration = Duration.ofSeconds(20)
    }

    install(SentryKtorFeature) {
        sentryTagsProvider = {
            val tags = mutableListOf<SentryKtorFeature.SentryTag>()
            it.traceId?.also { traceId ->
                tags.add(SentryKtorFeature.SentryTag("traceId", traceId))
            }
            it.requestId?.also { requestId ->
                tags.add(SentryKtorFeature.SentryTag("requestId", requestId))
            }

            tags
        }
    }

    install(StatusPages) {
        @Suppress("EXPERIMENTAL_API_USAGE")
        exception<MissingRequestParameterException> { call, exception ->
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("message" to "Request parameter ${exception.parameterName} is missing")
            )
            throw exception
        }

        exception<JsonProcessingException> { call, exception ->
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to exception.originalMessage))
            throw exception
        }
    }

    val httpComponent = HttpComponentFactory.build()

    routing {
        get("/") { call.respond(HttpStatusCode.OK, Unit) }
    }
}
