#!/bin/bash

set -x
set -e

ENV=$1

bash servers/ktor-external/deploy/build.sh ${ENV}

export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
kscript servers/ktor-external/deploy/deploy.kts ${ENV}

