#/bin/bash

function outputBuildComment() {
 echo "************************************"
 echo ""
 echo " " $1
 echo ""
 echo "************************************"
}

function run_assembly() {
	outputBuildComment "Building assembly"
	./sbt "project buildstatus-service" assembly

	outputBuildComment "Running assembly"
	java -jar dataload-service/target/capd-services.jar server configuration/local/services/buildstatusServiceConfiguration.yml
}

function run_dev() {
	outputBuildComment "Running dev"
./sbt "project buildstatus-service" "run server configuration/Local/services/myBuildstatusServiceConfiguration.yml"
}


REPO_DIR="$( cd "$( dirname "${BASH_SOURCE:-$0}" )" && pwd )"
cd $REPO_DIR	

if [ "$1" = "-a" ]; then
	run_assembly
else
	run_dev
fi



