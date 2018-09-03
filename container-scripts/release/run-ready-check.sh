
echo "=== Ready Check Starting ($HOSTNAME)"

if [ -z "$READYFILE_LOCATION" ]; then
    # Default Readyfile location
    READYFILE_LOCATION="/Readyfile"
fi  

# Readyfile shouldn't exist yet but let's make extra sure
rm $READYFILE_LOCATION

if [ "$FORCE_READY" ]; then
    echo "=== FORCE_READY environment variable present, skipping ready check"
    touch $READYFILE_LOCATION
    exit 0
fi  

echo "=== Waiting 5s for app to start... ($HOSTNAME)"
# This script should have been started in the background just before the app starts
sleep 5s

#curl the health endpoint to make sure the service is health before running unit tests
echo "=== Starting curl ($HOSTNAME)"
response=$(curl -k https://127.0.0.1:443/v1/health --write-out %{http_code} --silent --output ./curl.out --retry 3 --retry-delay 5)
echo "=== response code from curl request: $response ($HOSTNAME)"
echo "== Curl output: ($HOSTNAME)"
cat ./curl.out
echo "" # above output likely doesn't contain ending newline

if [ $response -eq 200 ]; then
    echo "=== curl health test result: SUCCESS ($HOSTNAME)"
    echo "Creating ready file"
    touch $READYFILE_LOCATION
    exit 0
else
    echo "=== curl health test result: FAILURE ($HOSTNAME)"
    echo "Ready check Failed!" > /NotReady
    exit 1 
fi