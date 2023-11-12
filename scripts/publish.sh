#!/bin/bash

# running validation
./gradlew ktlint detekt || (echo "invalid ktlint/detekt output"; exit 1)
./gradlew check || (echo "tests failed"; exit 1)

# simple implementation of delivering locally & then publicly
./gradlew publishToMavenLocal && ./gradlew publishAllPublicationsToSonatypeRepository closeAndReleaseStagingRepository
