import logging
import os
import boto3
import time
import sys
import requests
import traceback
from concurrent.futures import TimeoutError

# TODO:
# Implement your own tests here.  You should check each back-end dependency.
# This smoke test will run upon container start in all environments including production.
# Use system environment variables to retrieve the endpoints to different dependencies
# per environment.

# just an example
def verify_backing_service():
    try:
        # url = os.environ['BT_YOUR_DEPENDENCY_URL']
        url = "https://jsonplaceholder.typicode.com/posts"
        r = requests.post(
            url,
            json={"testValue": "123"},
            timeout = 10
            )
    except Exception as e:
        print("Error getting response from endpoint: {}\n".format(e))
        return False

    return True

# just an example
def verify_other_backing_service():
    try:
        # url = os.environ['BT_YOUR_OTHER_DEPENDENCY_URL']
        url = "https://jsonplaceholder.typicode.com/users"
        r = requests.get(
            url,
            timeout = 10
            )
    except Exception as e:
        print("Error getting response from endpoint: {}\n".format(e))
        return False

    return True



if __name__ == "__main__":

    print("Starting Smoke Test")

    if os.environ.get('BYPASS_SMOKETEST', ''):
        print("WARNING: BYPASS_SMOKETEST indicated, skipping smoke test! ")
        sys.exit(0)

    smoke_tests = [
        # {
        #     "testName": "Backing service (example, replace with a real test)",
        #     "testFunc": verify_backing_service
        # },
        # {
        #     "testName": "Other backing service (example, replace with a real test)",
        #     "testFunc": verify_other_backing_service
        # }
    ]

    all_passed = True
    for smoke_test in smoke_tests:
        print("Running test: {}".format(smoke_test['testName']))
        success = smoke_test['testFunc']()
        if not success:
            all_passed = False
        print("{} - {}{}".format(
            "PASS" if success else "FAIL",
            smoke_test['testName'],
            os.linesep
            ))

    print("SMOKE TEST END RESULT: {}".format("PASS" if all_passed else "FAIL"))
    sys.exit(0) if all_passed else sys.exit(1)
