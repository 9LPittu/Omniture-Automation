function waitForServerRestart {

	COUNTER=0
	echo 'Waiting for server to restart'
	until $(curl --output /dev/null --silent --head --fail http://www.sidecar-brn-ci.jcrew.com); do
		if [ $COUNTER -gt 18 ]; then
			echo 'Server startup timed out'
			return 1
		else 
			((COUNTER += 1)) 
			echo Retry $COUNTER
		 fi
		sleep 10
	done
}

waitForServerRestart
