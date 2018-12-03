#!/bin/sh

def_port=`sed -n '3p' ./src/main/resources/dev/application.yml | awk '{print $2}'`

PORT=`[ -z "$1" ] && echo "$def_port" || echo "$1"`

if [ "$PORT" -eq "$def_port" ]
then
    export MAVEN_OPTS="-Xmx2048m -Xms2048m -Xmn1024m -Xss512k -XX:PermSize=256m -XX:MaxPermSize=256m -XX:+CMSClassUnloadingEnabled -XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC -Xdebug -Xrunjdwp:transport=dt_socket,address=1311,suspend=n,server=y"
else
    unset MAVEN_OPTS
fi

mvn -Pdev clean spring-boot:run -Drun.arguments="--server.port=$PORT"