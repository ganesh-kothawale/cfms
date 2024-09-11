object Libs {
  const val kotlinVersion = "1.9.22"

  object KotlinUtils {
    private const val group = "in.porter.kotlinutils"
    private const val version = "0.71.0"

    const val serdeJackson = "$group:serde-jackson:$version"
    const val commons = "$group:commons:$version"
    const val exposed = "$group:exposed:$version"
    const val testing = "$group:testing:$version"
    const val openTracing = "$group:opentracing:$version"
    const val sentryKtorFeature = "$group:sentry-ktor-feature:$version"
    const val ktorWebServer = "$group:webserver-ktor:$version"
    const val awsSqs = "$group:aws-sqs:$version"
    const val awsS3 = "$group:aws-s3:$version"
    const val geos = "$group:geos:$version"
    const val ktorWebClient = "$group:webclient-ktor-features:$version"
  }

  object Dagger {
    private const val group = "com.google.dagger"
    private const val version = "2.50"

    const val dagger = "$group:dagger:$version"
    const val compiler = "$group:dagger-compiler:$version"
  }

  object Ktor {
    private const val version = "2.3.7"
    private const val utilsVersion = "1.6.8"
    private const val group = "io.ktor"

    const val clientCore = "$group:ktor-client-core:$version"
    const val clientCio = "$group:ktor-client-cio:$version"
    const val clientJson = "$group:ktor-client-json:$version"
    const val clientJackson = "$group:ktor-serialization-jackson:$version"

    const val serverCore = "$group:ktor-server-core:$version"
    const val serverNetty = "$group:ktor-server-netty:$version"
    const val serverJackson = "$group:ktor-serialization-jackson:$version"

    const val ktorHtml = "$group:ktor-html-builder:$utilsVersion"
    const val doubleReceive = "$group:ktor-server-double-receive:$version"
    const val forwardedHeaders = "$group:ktor-server-forwarded-header:$version"
    const val statusPages = "$group:ktor-server-status-pages:$version"
    const val serverContentNegotiation = "$group:ktor-server-content-negotiation:$version"


    const val ktorHttpClient = "$group:ktor-client-cio:$version"
    const val ktorOkHttpClient = "$group:ktor-client-okhttp:$version"
    const val contentNegotiation = "$group:ktor-client-content-negotiation:$version"

  }

  object Log4j {
    private const val version = "2.22.1"
    private const val group = "org.apache.logging.log4j"

    const val core = "$group:log4j-core:$version"
    const val slf4jImpl = "$group:log4j-slf4j-impl:$version"
  }

  object Testing {
    private const val testContainersGroup = "org.testcontainers"
    private const val testContainersVersion = "1.12.3"
    const val testContainers = "$testContainersGroup:testcontainers:$testContainersVersion"
    const val testContainersPostgresql = "$testContainersGroup:postgresql:$testContainersVersion"
    const val testContainersJuniper = "$testContainersGroup:junit-jupiter:$testContainersVersion"
  }

  const val caffeine = "com.github.ben-manes.caffeine:caffeine:2.8.0"
  const val hikariCP = "com.zaxxer:HikariCP:3.4.1"
  const val sentry = "io.sentry:sentry:1.7.27"
  const val postgresql = "org.postgresql:postgresql:42.2.8"
  const val elasticEcs = "co.elastic.logging:log4j2-ecs-layout:0.5.0"
  const val shadowTransformer = "com.github.jengelman.gradle.plugins:shadow:2.0.2"
  const val money = "org.javamoney:moneta:1.4.2"

  object Micrometer {
    private const val group = "io.micrometer"
    private const val version = "1.5.3"

    const val core = "$group:micrometer-core:$version"
    const val cloudwatch = "$group:micrometer-registry-cloudwatch2:$version"
  }

}
