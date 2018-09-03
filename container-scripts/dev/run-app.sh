#!/usr/bin/bash
set -e

echo "=== Starting nginx"
nginx
echo "=== Restoring packages for unit tests"
cd ./creditapp-services
dotnet restore
cd ..
echo "=== Restoring packages for application"
cd ./CollectionServices
dotnet restore
echo "=== Starting application in development watch mode"
dotnet watch run
