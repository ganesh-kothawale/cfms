import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer

plugins {
  application
  id(Plugins.fatJarPlugin).version(Plugins.fatJarPluginVersion)
  id(Plugins.kotlinKapt)
}

val env = project.properties["env"] as String? ?: "dev"

dependencies {
  implementation(project(Modules.CFMS.domain))
  implementation(project(Modules.CFMS.Data.psql))
  implementation(project(Modules.CFMS.Api.service))
  implementation(project(Modules.CFMS.Api.models))
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

tasks.register<Delete>("cleanResources") {
  delete("build/config")
}

tasks.register<Copy>("copyConfig") {
  // Delete the config directory before copying
  doFirst {
    delete("build/config")
  }

  // Define all copy specifications
  mapOf(
    "config/dev/dom/common" to "dom",
    "config/dev/dom/ktor" to "dom",
    "config/dev/common" to "dom"
  ).forEach { (from, to) ->
    println("Copying from $from to $to")
    from(project.rootDir.resolve(from))
    into("build/config/$to")
  }
}

if (env == "dev") {
  tasks.named("processResources") {
    dependsOn("copyConfig")
  }
}

tasks.findByName("clean")?.dependsOn("cleanResources")
tasks.findByName("processResources")?.dependsOn("copyConfig")

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
  mainClass.set("in.porter.cfms.servers.ktor.app.MainRunnerKt")
}
