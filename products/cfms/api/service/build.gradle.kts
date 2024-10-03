dependencies {
  implementation(project(Modules.CFMS.domain))
  implementation(project(Modules.CFMS.Api.models))
  implementation(Libs.KotlinUtils.openTracing)
  testImplementation(Libs.Testing.testContainersPostgresql)
}
