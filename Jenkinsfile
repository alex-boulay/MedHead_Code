def outSpringFile = 'outSpringFile.txt'
def maxWaitTimeSeconds = 120
def waitIntervalSeconds = 5

pipeline {
    agent any
    environment {
        PID = ''
    }
    stages {        
        stage('Build') {
            steps {
                echo 'Building'
				bat 'mvn clean install -DskipTests=true'
                //bat 'mvn clean install'
            }
        }
        
        stage('Spring Thread') {
			steps {
                script {
					def pid = powershell(script: '''
						# Start process and capture process info
						$processInfo = Start-Process -NoNewWindow -PassThru cmd "/c mvn spring-boot:run > outSpringFile.txt"
						
						# Return the PID (process ID) for later use
						return $processInfo.Id
						''', returnStdout: true).trim()

                    echo "The PID is ${pid}"
					
					def startTime = currentBuild.startTimeInMillis
					def elapsedTime = 0
					def doneInitializing = false
					
					while (elapsedTime < maxWaitTimeSeconds * 1000 && !doneInitializing) {
						if (fileExists(outSpringFile)) {
						}
						doneInitializing = bat "Get-Content ${outSpringFile} | %{$_ -match \"DONE INITIALISING\"})"
						
						sleep(waitIntervalSeconds * 1000)
						elapsedTime = System.currentTimeMillis() - startTime
					}
					
					if (!doneInitializing) {
						error "Application initialization timed out"
					}
				}
			}
        }
        
        stage('Run Postman Collection') {
            steps {
                // Run Postman collection using Newman
                bat 'newman.cmd run ./P11_MedHead.postman_collection.json --insecure'
            }
        }
        
        stage('Execute JMeter Tests') {
            steps {
                // Execute JMeter performance tests
                bat 'jmeter -n -t src/main/resources/MedHeadPerfTest.jmx'
            }
        }
    }
    
    post {
		always {
		// Stop the Spring application
			script {
				powershell '''
					# Get the PID from the environment variable
					$pid = '${env.PID}'
					
					# When you want to stop the service, use the PID:
					Stop-Process -Id $pid
				'''
			}
		}
        success {
            when {
                expression {
                    BRANCH_NAME == 'dev'
                }
            }
            steps {
                bat 'git merge -X master'
            }
        }
    }
}