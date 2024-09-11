plugins {
  kotlin(Plugins.kotlinxSerialization) version Libs.kotlinVersion
  id(Plugins.flywayPlugin) version (Plugins.flywayVersion)
  id(Plugins.kotlinKapt)
}

dependencies {
  implementation(project(Modules.KPTR.domain))
  implementation(project(Modules.KPTR.Data.psql))

  implementation(Libs.Log4j.core)
  implementation(Libs.Log4j.slf4jImpl)

  implementation(Libs.Micrometer.cloudwatch)

  implementation(Libs.Ktor.clientCio)
  implementation(Libs.Ktor.clientJson)
  implementation(Libs.Ktor.clientJackson)
  implementation(Libs.Ktor.contentNegotiation)
  implementation(Libs.Ktor.serverContentNegotiation)
  implementation(Libs.Ktor.statusPages)
  implementation(Libs.Ktor.doubleReceive)
  implementation(Libs.Ktor.forwardedHeaders)

  implementation(Libs.KotlinUtils.exposed)
  implementation(Libs.hikariCP)

  implementation(Libs.sentry)

  implementation(Libs.KotlinUtils.openTracing)
  implementation(Libs.KotlinUtils.awsSqs)
  implementation(Libs.KotlinUtils.awsS3)
  implementation(Libs.KotlinUtils.serdeJackson)
  implementation(Libs.KotlinUtils.ktorWebClient)

  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)

  implementation(Libs.elasticEcs)
  implementation(Libs.shadowTransformer)
}

val env = project.properties["env"] as String? ?: "dev"
val resourcesFolder = "src/main/resources"
val configFolder = "src/main/config"

tasks.register<Copy>("copyResources") {
  from("$configFolder/$env")
  into(resourcesFolder)
}

tasks.findByName("processResources")
  ?.dependsOn("copyResources")

tasks.register<Delete>("cleanResources") {
  File("$projectDir/$resourcesFolder").listFiles()
    ?.forEach { it.delete() }
}

project.tasks.findByName("flywayMigrate")

tasks
  .findByName("clean")
  ?.dependsOn("cleanResources")
