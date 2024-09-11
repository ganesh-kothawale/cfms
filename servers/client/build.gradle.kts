plugins {
  id(Plugins.kotlinKapt)
  id(Plugins.mavenPublish)
  id(Plugins.mavenS3) version Plugins.mavenS3Version
  id(Plugins.sonaTypePlugin) version Plugins.sonaTypePluginVersion
}

dependencies {
  api(project(Modules.KPTR.Api.models))
  implementation(Libs.KotlinUtils.serdeJackson)
  implementation(Libs.KotlinUtils.awsSqs)

  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)

}
val sourceJar = task("sourceJar", Jar::class) {
  dependsOn(JavaPlugin.CLASSES_TASK_NAME)
  archiveClassifier.set("sources")
  from(project.the<SourceSetContainer>()["main"].allSource)
}


publishing {
  publications {
    create<MavenPublication>(name) {
      artifactId = "kptr-client"

      from(components["java"])
      artifact(sourceJar)
    }
  }

  repositories {
    maven {
      url = uri("s3://porter-maven/releases")
      credentials(AwsCredentials::class, mavenS3.credentials())
    }
  }
}
