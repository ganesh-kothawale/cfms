#!/usr/bin/env kscript

//KOTLIN_OPTS -cp ecs-deployer.jar
//COMPILER_OPTS -cp ecs-deployer.jar

import `in`.porter.utilities.ecsdeployer.dsl.*

val env = args[0]

//INCLUDE components.kt

val taskDesiredCount =
  when (env) {
    "prod" -> 2
    else -> 1
  }

createService {
  cluster = if (env == "prod") "ecs-prod-private-v2" else "ecs-$env-private"
  name = appName
  task = serverTask

  desiredCount = taskDesiredCount
  deploymentConfiguration {
    maxPct = 200
    minHealthyPct = 100
  }
}
