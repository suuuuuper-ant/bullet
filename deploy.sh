#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/bullet
cd $REPOSITORY

APP_NAME=digin
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

source /home/ubuntu/.bashrc
source /home/ubuntu/set-env.sh

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"

#JAR_OPTS="-Dspring.profiles.active=dev -Dspring.datasource.username=$DB_USER -Dspring.datasource.password=$DB_PW -Dcloud.aws.credentials.secret-key=$AWS_SECRET_KEY -Dcloud.aws.credentials.access-key=$AWS_ACCESS_KEY"
JAR_OPTS="-Dspring.profiles.active=dev
-Dbullet.database.host=$DB_HOST
-Dbullet.database.database=$DB_NAME
-Dbullet.database.username=$DB_USER
-Dbullet.database.password=$DB_PW
-Dcloud.aws.credentials.secret-key=$AWS_SECRET_KEY
-Dcloud.aws.credentials.access-key=$AWS_ACCESS_KEY"

nohup java -jar $JAR_OPTS $JAR_PATH > /dev/null 2> /dev/null < /dev/null &

#sudo nohup java -jar $JAR_OPTS $JAR_PATH > $REPOSITORY/nohup.out 2>&1 &