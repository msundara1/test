#!/usr/bin/bash
set -e

echo "=== Starting nginx"
nginx
echo "=== Launching ready checker in background"
nohup bash /scripts/run-ready-check.sh &
echo "=== Starting app"
cd /app
dotnet SeedApplication.dll