#!/usr/bin/env bash
function waitForServerRestart {

	RETRY_NUMBER=0
	MAX_NUMBER_OF_RETRIES=18
	echo 'Waiting for server to restart'
	until $(curl --output /dev/null --silent --head --fail http://www.sidecar-brn-.jcrew.com); do
		if [ ${RETRY_NUMBER} -ge ${MAX_NUMBER_OF_RETRIES} ]; then
			echo 'Server startup timed out'
			return 1
		else 
			((RETRY_NUMBER += 1))
			echo Retry ${RETRY_NUMBER}
		 fi
		sleep 10
	done
}

waitForServerRestart
