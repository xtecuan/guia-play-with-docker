#!/bin/bash

docker run -it --rm --name my-maven-project -v "$(pwd)":/tmp/src -w /tmp/src maven:3.8.6-openjdk-11 mvn clean install
docker build -t mipayara .