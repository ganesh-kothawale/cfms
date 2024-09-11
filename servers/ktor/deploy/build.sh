#!/bin/bash

set -x
set -e

ENV=$1
MODULE_PATH="servers/ktor"
SERVER_COMMONS_PATH="servers/commons"

ECS_DEPLOYER_VERSION="0.7.0"
ECS_DEPLOYER_S3_FOLDER="s3://porter-maven/applications/in/porter/utilities/ecs-deployer/${ECS_DEPLOYER_VERSION}/"
ECS_DEPLOYER_S3_JAR="ecs-deployer-${ECS_DEPLOYER_VERSION}-all.jar"
ECS_DEPLOYER_LOCAL_JAR="ecs-deployer.jar"

aws s3 sync ${ECS_DEPLOYER_S3_FOLDER} . --exclude '*' --include ${ECS_DEPLOYER_S3_JAR}
cp ${ECS_DEPLOYER_S3_JAR} ${ECS_DEPLOYER_LOCAL_JAR}

# Downloading config logic goes here
#aws s3 cp s3://porter-configs/${ENV}/kptr/psql_secrets.properties ${MODULE_PATH}/src/main/config/${ENV}/

SHADOW_JAR_FILE="ktor-server-all.jar"
JAR_FILE="ktor-server.jar"

./gradlew --console=plain -Penv=${ENV} clean ktor-server:shadowJar
cp ${MODULE_PATH}/build/libs/${SHADOW_JAR_FILE} ${MODULE_PATH}/deploy/server/${JAR_FILE}


DATADOG_FLDR="${MODULE_PATH}/deploy/server/datadog"
mkdir -p ${DATADOG_FLDR}
aws s3 sync s3://porter-maven/libraries/com/datadoghq/dd-java/agent/0.47.0/ ${DATADOG_FLDR}  --exclude '*' --include 'dd-java-agent.jar'
cp ${MODULE_PATH}/src/main/config/"${ENV}"/datadog.properties ${DATADOG_FLDR}
