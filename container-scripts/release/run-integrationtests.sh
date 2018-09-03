#!/usr/bin/bash
set -e

echo "=== Starting nginx"
nginx

echo "=== Starting application in background"
cd /app
nohup dotnet SeedApplication.dll &

echo "=== Waiting 5s for app to start..."
sleep 5s

echo "=== Starting Newman"
cd /postman
newman run integration_tests.json --disable-unicode -r cli,json --reporter-json-export outputfile.json

if [ $? -eq 0 ]; then
    echo "=== Integration test result: SUCCESS"
    python postman_results_to_csv.py 'seed-ecs-dotnet-rest' 'seed-ecs-dotnet-rest' 'pass'
    exit 0
else
	echo "=== Integration test result: FAILURE"
    python postman_results_to_csv.py 'seed-ecs-dotnet-rest' 'seed-ecs-dotnet-rest' 'fail'
	exit 1
fi
