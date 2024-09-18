plugins {
  id(Plugins.kotlinKapt)
}
dependencies {
  implementation(project(Modules.CFMS.domain))
  implementation(Libs.KotlinUtils.openTracing)
  implementation(Libs.KotlinUtils.exposed)
  implementation(Libs.KotlinUtils.geos)
  implementation(Libs.Micrometer.core)
  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)
  testImplementation(Libs.Testing.testContainersPostgresql)
}
