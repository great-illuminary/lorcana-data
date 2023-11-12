#!/bin/bash

set -xe

# running validation
./gradlew ktlint detekt
./gradlew check

# simple implementation of delivering locally & then publicly
./gradlew publishToMavenLocal && ./gradlew publishAllPublicationsToSonatypeRepository closeAndReleaseStagingRepository
