import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
  id(Plugins.kotlinJvm) version Libs.kotlinVersion apply false
  id(Plugins.Detekt.detektLib) version (Plugins.Detekt.version)
  id(Plugins.mavenS3) version Plugins.mavenS3Version
  id (Plugins.sonaTypePlugin) version Plugins.sonaTypePluginVersion
  id(Plugins.Jacoco.plugin)
  id(Plugins.SonarQube.plugin) version (Plugins.SonarQube.version)
}

allprojects {
  apply(plugin = Plugins.Detekt.detektLib)
  apply(plugin = Plugins.mavenS3)

  detekt {
    toolVersion = Plugins.Detekt.version
    config = files("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = false
  }

  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
      url = uri("https://repos.spring.io/plugins-release")
    }


    maven {
      url = uri("s3://porter-maven/releases")
      credentials(AwsCredentials::class, mavenS3.credentials())
    }

  }
}

subprojects {
  group = "in.porter.kptr"
  version = "0.1.0"

  apply(plugin = Plugins.kotlinJvm)
  apply(plugin = Plugins.Jacoco.plugin)

  dependencies {
    "implementation"(Libs.KotlinUtils.commons)
    "testImplementation"(Libs.KotlinUtils.testing)
  }

  // Configure Jacoco for subprojects
  jacoco {
    toolVersion = Plugins.Jacoco.version
  }

  tasks.withType<Test> {
    @Suppress("UnstableApiUsage")
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
  }

  tasks.withType<JacocoReport> {
    dependsOn("test") // Ensure tests are run before generating the report

    reports {
      xml.required.set(true)
    }
  }

  kotlinExtension.jvmToolchain(11)
}
