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
  implementation(project(Modules.Servers.client))

  implementation(project(Modules.Servers.commons))


  implementation(Libs.Log4j.core)
  implementation(Libs.Log4j.slf4jImpl)

  implementation(Libs.Micrometer.cloudwatch)

  implementation(Libs.Ktor.clientCio)
  implementation(Libs.Ktor.clientJson)
  implementation(Libs.Ktor.clientJackson)


  implementation(Libs.hikariCP)

  implementation(Libs.sentry)



  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)

  implementation(Libs.elasticEcs)
  implementation(Libs.shadowTransformer)

  implementation(Libs.KotlinUtils.serdeJackson)
  implementation(Libs.KotlinUtils.exposed)
  implementation(Libs.KotlinUtils.openTracing)
  implementation(Libs.KotlinUtils.awsSqs)
  implementation(Libs.KotlinUtils.distributedlocksApi)
  implementation(Libs.KotlinUtils.locksRedission)
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
  mainClass.set("in.porter.cfms.servers.sqs.app.MainRunnerKt")
}
