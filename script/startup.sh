#!/bin/bash
BASE_DIR=$(dirname "$0")
cd BASE_DIR || exit

[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "$JAVA_HOME/bin/java" ] && error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)!"

export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"

# add all jar file and current dir into java classpath
PROJECT_CLASS_PATH="."
for file in "$BASE_DIR"/lib/*.jar;
do
    PROJECT_CLASS_PATH="$PROJECT_CLASS_PATH:$file"
done

nohup $JAVA -cp "$PROJECT_CLASS_PATH" top.aziraphale.RayServerStarter >/dev/null 2>&1 &