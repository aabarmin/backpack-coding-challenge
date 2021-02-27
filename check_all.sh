#!/bin/sh

chmod +x ./check_*.sh

./check_maven.sh
./check_gradle.sh
./check_style.sh