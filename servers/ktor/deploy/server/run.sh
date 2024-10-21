#!/usr/bin/env bash

function _log {
  echo "$(date +"%F %T") ${SELF_NAME} $1"
}
_log "starting the server"

echo "CONFG_PATH=$CONFIG_PATH"
echo "pwd=$(pwd)"

echo "Staring java run"

java \
  -javaagent:datadog/dd-java-agent.jar \
  -Ddd.trace.config=${CONFIG_PATH}/properties/datadog.properties \
  -Dlog4j.configurationFile="${CONFIG_PATH}/properties/log4j2.yml" \
  -Ddd.agent.port=8126 \
  -jar ktor-server.jar