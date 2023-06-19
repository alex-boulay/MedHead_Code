def outSpringFile = 'outSpringFile.txt'
def maxWaitTimeSeconds = 120
def waitIntervalSeconds = 5
def pid = -1

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
					pid = powershell(script: '''
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
						echo "Inside While"
						if (fileExists(outSpringFile)) {
							doneInitializing = powershell(returnStdout: true, script: "Get-Content ${outSpringFile} | %{$_ -match 'DONE INITIALISING'}").trim().toBoolean()
							
							echo "fileExists , done ? ${doneInitializing}"
						}
						sleep(waitIntervalSeconds * 1000)
						elapsedTime = System.currentTimeMillis() - startTime
						echo " Time elapsed ${(elapsedTime/1000)} s"
					}
					if (!doneInitializing) {
					echo "Timed Out"
						error "Application initialization timed out"
					}
					else {
						echo "INIT DONE"
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
			bat "Taskkill /F /PID ${pid}"
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