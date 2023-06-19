def outSpringFile = 'outSpringFile.txt'
def maxWaitTimeSeconds = 120
def waitIntervalSeconds = 5

pipeline {
    agent any

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
				// Lancement de Spring et enregistrement du PID pour le terminer ultérieurement
				bat "start \"service1\" mvn spring-boot:run > ${outSpringFile}"
				
				script {
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
                bat 'taskkill /FI \"WindowTitle eq service1*\" /T /F'
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