#!/usr/bin/bash

apt-get update -y

yes | apt-get install curl

curlResult = $(curl -s -o /dev/null -I -w "%{http_code}" http://config-server:8888/actuator/health)

echo "results" $curlResult

while [[ ! $curlResult == "200"]]; do
  >&2 echo "Config server is not up yet"
  sleep 2
  curlResult = $(curl -s -o /dev/null -I -w "%{http_code}" http://config-server:8888/actuator/health)
done

# Original entrypoint
./cmb/lifecycle/launcher