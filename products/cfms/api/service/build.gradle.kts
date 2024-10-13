dependencies {
    implementation(project(Modules.CFMS.domain))
    implementation(project(Modules.CFMS.Api.models))
    implementation(Libs.KotlinUtils.openTracing)
    implementation(Libs.KotlinUtils.serdeJackson)
    testImplementation(Libs.Testing.testContainersPostgresql)
}
