#!/usr/bin/bash
set -e

echo "=== Starting nginx"
nginx
echo "=== Building application"
cd /app
dotnet build
echo "=== Starting application in background"
nohup java -jar creditapp-service.jar &
echo "=== Waiting 5s for app to start..."
sleep 5s
echo "=== Starting Newman"
python run-tests.py
if [ $? -eq 0 ]; then
    echo "=== Integration test result: SUCCESS"
    exit 0
else
	echo "=== Integration test result: FAILURE"
	exit 1
fi
