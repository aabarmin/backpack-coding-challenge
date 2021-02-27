#!/bin/sh

chmod +x ./mvnw
./mvnw checkstyle:check -f ./packer-lib/pom.xml