rootProject.name = "cfms"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

include(":domain")
project(":domain").projectDir = File("products/cfms/domain")

include("data-psql")
project(":data-psql").projectDir = File("products/cfms/data/psql")

include("api-models")
project(":api-models").projectDir = File("products/cfms/api/models")

include("api-service")
project(":api-service").projectDir = File("products/cfms/api/service")

include(":server-commons")
project(":server-commons").projectDir = File("servers/commons")

include(":ktor-server")
project(":ktor-server").projectDir = File("servers/ktor")

include(":ktor-external")
project(":ktor-external").projectDir = File("servers/ktor-external")

include(":sqs-server")
project(":sqs-server").projectDir = File("servers/sqs")

include(":client")
project(":client").projectDir = File("servers/client")

//includeBuild("../kotlin-utils")
