#!/bin/sh

# export APPLICATION_SERVER="ThornTail micro"

mvn clean package && java -jar hollow-thorntail.jar target/shop.war