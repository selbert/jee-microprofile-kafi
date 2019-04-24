#!/bin/sh
mvn clean package && docker-compose up -d --build

exec docker-compose logs -f