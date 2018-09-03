# -*- coding: utf-8 -*-
import boto3
import os
import json
import sys
import argparse
import subprocess
import time


def exec_command(command):
    print("Executing: {}".format(command))
    output = ""
    try:
        # output = os.popen(command).read()
        p = subprocess.Popen(command, stdout=subprocess.PIPE, shell=True)
        p_status = p.wait()
        (output, err) = p.communicate()
        output = output.decode("utf-8")
    except Exception as e:
        print("Error: Output: {} \nException:{}".format(output, str(e)))
        return (1, output, -1)
    return (p.returncode, output, p.pid)


def load_ssm_envvars(ssm_path):
    session = boto3.session.Session()

    client = session.client('ssm')
    nextToken = ""
    fullParams = []

    print("Reading parameters from SSM path: {}".format(ssm_path))
    try:
        response = client.get_parameters_by_path(
            Path=ssm_path,
            Recursive=True,
            WithDecryption=True
        )
        fullParams.extend(response['Parameters'])
        if 'NextToken' in response:
            nextToken = response['NextToken']
    except Exception as e:
        print("Error querying list of ssm parameters: {}".format(str(e)))
        raise

    while (nextToken):
        try:
            response = client.get_parameters_by_path(
                Path=ssm_path,
                Recursive=True,
                WithDecryption=True,
                NextToken=nextToken
            )
            fullParams.extend(response['Parameters'])
            if 'NextToken' in response:
                nextToken = response['NextToken']
            else:
                nextToken = ""
        except Exception as e:
            print("Error querying list of ssm parameters: {}".format(str(e)))
            raise

    print("Read {} parameters from SSM".format(len(fullParams)))

    for parameter in fullParams:
        try:
            envvar_name = parameter['Name'].split('/')[-1]
            envvar_value = parameter['Value']
            envvar_type = parameter['Type']
            if envvar_name in os.environ:
                print("{} already in environment".format(envvar_name))
            else:
                if not envvar_type == 'SecureString':
                    print(
                        "{} - setting value from ssm: {}".format(envvar_name, envvar_value))
                else:
                    print(
                        "{} - setting value from ssm (SecureString, {} chars)".format(envvar_name, len(envvar_value)))
                os.environ[envvar_name] = envvar_value
        except Exception as e:
            print("Error processing parameter: {}".format(str(e)))

    # print("ALL ENV: {}".format(os.environ))


if __name__ == '__main__':

    parser = argparse.ArgumentParser()
    parser.add_argument('--ssm-name', required=True)
    parser.add_argument('--dev-mode', action="store_true", default=False)
    parser.add_argument('--test-mode', action="store_true", default=False)
    args = parser.parse_args()

    try:
        aws_env = os.environ['AWS_ENV']
        region = os.environ['AWS_REGION']
        os.environ["AWS_DEFAULT_REGION"] = region
        ssm_name = args.ssm_name
        dev_mode = args.dev_mode
        test_mode = args.test_mode
    except KeyError as e:
        print("Missing environment variable: {}".format(str(e)))
        print("AWS_ENV and AWS_REGION are required variables for this script to function.")
        sys.exit()

    # NOTE: run with PYTHONUNBUFFERED to see stdout in docker logs
    print("Container start script starting: AWS_ENV: {} AWS_REGION: {}".format(
        aws_env,
        region
    ))

    load_ssm_envvars(
        '/{}/{}/'.format(
            aws_env,
            ssm_name
        )
    )

#    print("Running Smoketest")
#    code, output, pid = exec_command("python3 /smoketest.py")
#    print(output)
#    if not code == 0:
#        print("Exiting due to smoke test failure")
#        time.sleep(1)
#        sys.exit(1)

    print("Starting nginx")
    os.system("nginx")

    print("Starting application")
    if dev_mode:
        print("Starting in development watch mode")
        os.chdir("./target")
        os.system("java -r creditapp-service.jar")
    elif test_mode:
        errors = []
        os.system("confd -onetime -backend env")
        os.system("nohup java -r creditapp-service.jar &")
        time.sleep(5)
        os.chdir("/postman")
        for filename in os.listdir():
            if filename.endswith(".json"):
                command = "newman run %s -d integration_tests_data.json --disable-unicode " % filename
                code, output, pid = exec_command(command)
                print(output)
                if not code == 0:
                    print("Postman test failure.")
                    errors.append(filename)
        if not len(errors) == 0:
            print("Integration test failure. Failed scripts:")
            for filename in errors:
                print(filename)
            # sys.exit(1)
        else:
            print("Postman run successful")
            sys.exit(0)
    else:
        os.chdir("./target")
        os.system("java -r creditapp-service.jar")
