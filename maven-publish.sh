#!/usr/bin/env bash

./gradlew -Psigning.keyId=$SIGNING_KEYID \
          -Psigning.password=$SIGNING_PASSWORD \
          -Psigning.secretKeyRingFile=$TRAVIS_BUILD_DIR/$SIGNING_FILE \
          -PossrhUsername=$OSSRH_USERNAME \
          -PossrhPassword=$OSSRH_PASSWORD \
          dokka publish