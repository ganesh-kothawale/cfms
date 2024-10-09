import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
  id(Plugins.kotlinJvm) version Libs.kotlinVersion apply false
  id(Plugins.mavenS3) version Plugins.mavenS3Version
  id (Plugins.sonaTypePlugin) version Plugins.sonaTypePluginVersion
  id(Plugins.artifactRegistry) version Plugins.artifactRegistryVersion apply true
  id(Plugins.Jacoco.plugin)
  id(Plugins.SonarQube.plugin) version (Plugins.SonarQube.version)
}

allprojects {
  apply(plugin = Plugins.mavenS3)
  apply(plugin = Plugins.artifactRegistry)

  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
      url = uri("https://repo.spring.io/plugins-release")
    }

    maven {
      url = uri("https://packages.confluent.io/maven/")
    }

    maven {
      url = uri("s3://porter-maven/releases")
      credentials(AwsCredentials::class, mavenS3.credentials())
    }
  }
}

subprojects {
  group = "in.porter.cfms"
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

  sonar {
    properties {
      property("sonar.coverage.exclusions", buildString {
        append("**/in/porter/cfms/servers/ktor/external/app/MainApplication.kt,")//make sure to add comma
      })
      property("sonar.gradle.skipCompile", true)
    }
  }

  tasks.withType<JacocoReport> {
    dependsOn("test") // Ensure tests are run before generating the report

    reports {
      xml.required.set(true)
    }
  }


  kotlinExtension.jvmToolchain(11)
}
