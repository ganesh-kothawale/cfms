#!/usr/bin/env bash

function _log {
  echo "$(date +"%F %T") ${SELF_NAME} $1"
}

_log "starting the server"


DD_AGENT_HOST=$(curl http://169.254.169.254/latest/meta-data/local-ipv4)
export DD_AGENT_HOST

java \
  -javaagent:datadog/dd-java-agent.jar \
  -Ddd.trace.config=datadog/datadog.properties \
  -Ddd.agent.port=8126 \
  -jar ktor-server.jar
