#!/bin/bash

set -xe

bash ./scripts/check.sh

# simple implementation of delivering locally & then publicly
./gradlew publishToMavenLocal && ./gradlew publishAllPublicationsToSonatypeRepository closeAndReleaseStagingRepository
