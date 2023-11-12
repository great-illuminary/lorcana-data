#!/bin/bash

set -xe

# generation, to be sure about the current state
./gradlew generateMR generateBuildKonfig

# running validation
./gradlew ktlint detekt
./gradlew check
