#!/bin/bash

set -xe

# generation, to be sure about the current state
./gradlew generateBuildKonfig --no-daemon

# running validation --no-daemon
./gradlew ktlint detekt --no-daemon
./gradlew check
