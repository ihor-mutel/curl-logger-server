#!/usr/bin/env sh

echo "## Start JAVA APP"
echo $JVM_OPTS

exec java $JVM_OPTS -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom  -jar *.jar