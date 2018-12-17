#!/usr/bin/env bash
mvn clean install && scp -P 2222 target/etorobot-0.1-SNAPSHOT.jar oleg@localhost:~/etoro-trading-api/target
ssh -L12300:localhost:12300 -p 2222 oleg@localhost << EOF
cd etoro-trading-api
./start.sh
EOF
