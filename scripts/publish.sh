#!/bin/bash

# simple implementation of delivering locally & then publicly
./gradlew publishToMavenLocal && ./gradlew publishAllPublicationsToSonatypeRepository closeAndReleaseStagingRepository
