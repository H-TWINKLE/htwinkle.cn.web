MAIN_CLASS=cn.htwinkle.devotion.config.MainConfig
APP_BASE_PATH=$(cd `dirname $0`; pwd)
CP=${APP_BASE_PATH}/config:${APP_BASE_PATH}/lib/*
java -Xverify:none ${JAVA_OPTS} -cp ${CP} ${MAIN_CLASS} &
