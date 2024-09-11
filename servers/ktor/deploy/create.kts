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

val serviceNetwork = network {
  healthCheckGracePeriod = 30.seconds()

  loadBalancer {
    name = if (env == "prod") "commons-prod-porter" else "commons-staging-2-porter"

    container = serverContainer
    port = 8080

    targetGroup {
      name = appName
      port = 8080

      healthCheckSettings {
        path = "/"

        healthyThresholdCount = 3
        unhealthyThresholdCount = 3

        healthCheckTimeout = 10.seconds()
        healthCheckInterval = 15.seconds()
      }
    }
  }

  dns {
    hostedZones {
      +HostedZoneDSL.PorterInternalDSL
      if (env == "staging") +HostedZoneDSL.PorterExternalDSL
    }
    name = "kptr-$env-ktor"
  }
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
  network = serviceNetwork
}
