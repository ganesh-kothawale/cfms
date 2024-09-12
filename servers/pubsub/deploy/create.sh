#!/bin/bash

set -x
set -e

ENV=$1


bash servers/sqs/deploy/build.sh ${ENV}

export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
kscript servers/sqs/deploy/create.kts ${ENV}
