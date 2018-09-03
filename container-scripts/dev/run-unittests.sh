#!/usr/bin/bash
set -e

echo "Executing unit tests"
cd /app
mvn test
