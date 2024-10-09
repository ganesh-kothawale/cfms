package `in`.porter.cfms.client.config

import io.ktor.http.*

data class ClientConfig(
  val protocol: URLProtocol,
  val host: String,
  val port: Int? = null
) {

  companion object {
    val Staging = ClientConfig(
      protocol = URLProtocol.HTTPS,
      host = "cfms-staging-ktor.porter.in"
    )

    val Production = ClientConfig(
      protocol = URLProtocol.HTTP,
      host = "cfms-prod-ktor.internal.porter.in"
    )
  }
}
