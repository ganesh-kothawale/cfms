import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer

plugins {
  application
  id(Plugins.fatJarPlugin).version(Plugins.fatJarPluginVersion)
  id(Plugins.kotlinKapt)
}

val env = project.properties["env"] as String? ?: "dev"

dependencies {
  implementation(project(Modules.KPTR.domain))
  implementation(project(Modules.KPTR.Data.psql))
  implementation(project(Modules.KPTR.Api.service))
  implementation(project(Modules.KPTR.Api.models))
  implementation(project(Modules.Servers.commons))
  implementation(project(Modules.Servers.client))



  implementation(Libs.KotlinUtils.serdeJackson)
  implementation(Libs.KotlinUtils.sentryKtorFeature)
  implementation(Libs.KotlinUtils.ktorWebServer)

  implementation(Libs.Log4j.core)
  implementation(Libs.Log4j.slf4jImpl)

  implementation(Libs.Ktor.serverCore)
  implementation(Libs.Ktor.serverNetty)
  implementation(Libs.Ktor.serverJackson)
  implementation(Libs.Micrometer.cloudwatch)
  implementation(Libs.Ktor.serverContentNegotiation)
  implementation(Libs.Ktor.statusPages)
  implementation(Libs.Ktor.doubleReceive)
  implementation(Libs.Ktor.forwardedHeaders)

  implementation(Libs.Ktor.clientCio)
  implementation(Libs.Ktor.clientJson)
  implementation(Libs.Ktor.clientJackson)

  implementation(Libs.KotlinUtils.exposed)
  implementation(Libs.hikariCP)

  implementation(Libs.sentry)

  implementation(Libs.KotlinUtils.openTracing)
  implementation(Libs.KotlinUtils.awsSqs)

  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)

  implementation(Libs.elasticEcs)
  implementation(Libs.shadowTransformer)
}

val configFolder = "src/main/config"
val resourcesFolder = "src/main/resources"

tasks.register<Copy>("copyResources") {
  from("$configFolder/$env")
  into(resourcesFolder)
}
tasks.findByName("processResources")?.dependsOn("copyResources")

tasks.register<Delete>("cleanResources") {
  File("$projectDir/$resourcesFolder").listFiles()
    ?.forEach { it.delete() }
}
tasks.findByName("clean")?.dependsOn("cleanResources")

// Adding transformer because of the following issues
// https://github.com/johnrengelman/shadow/issues/207
tasks.withType<ShadowJar> {
  archiveFileName.set("${archiveBaseName.get()}-${archiveClassifier.get()}.${archiveExtension.get()}")
  transform(Log4j2PluginsCacheFileTransformer())
}

tasks {
  "shadowJar"(ShadowJar::class) {
    isZip64 = true
  }
}

application {
  mainClass.set("in.porter.kptr.servers.ktor.external.app.MainRunnerKt")
}
