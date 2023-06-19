def processIdFile = 'process_id.txt'
def maxWaitTimeSeconds = 120
def waitIntervalSeconds = 5

pipeline {
    agent any

    stages {        
        stage('Build') {
            steps {
                echo 'Building'
                bat 'mvn clean install'
            }
        }
        
        stage('Spring Thread') {
            steps {
				// Lancement de spring et enregistrement du PID pour kill
                bat 'start /B cmd /C "mvn spring-boot:run >nul 2>&1 & echo %PROCESS_ID% > %WORKSPACE%\\' + processIdFile + '"'
                
                // Script pour attendre l'initialisation de Spring
                script {
                    def startTime = currentBuild.startTimeInMillis
                    def elapsedTime = 0
                    def processId = ''
                    def doneInitializing = false
                    
                    while (elapsedTime < maxWaitTimeSeconds * 1000 && !doneInitializing) {
                        if (fileExists(processIdFile)) {
                            processId = readFile(processIdFile).trim()
                        }
                        
                        def consoleOutput = bat(script: "powershell -Command \"Get-Process -Id ${processId} | Select-Object -ExpandProperty MainWindowTitle\"", returnStdout: true)
                        doneInitializing = consoleOutput.contains('DONE INITIALISING')
                        
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
                def processId = readFile(processIdFile).trim()
                bat "taskkill /F /PID ${processId}"
				deleteFile processIdFile
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