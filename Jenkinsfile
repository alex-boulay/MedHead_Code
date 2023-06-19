pipeline {
    agent any

    stages {        
        stage('Build') {
            steps {
                echo 'Building'
                sh 'mvn clean install'
            }
        }
        
        stage('Start App') {
            steps {
                echo 'Starting the Spring app'
                sh 'java -jar target/MedHead.jar &'
            }
        }
        
        stage('Run Postman Collection') {
            steps {
                // Run Postman collection using Newman
                sh 'newman run P11_MedHead.postman_collection.json'
            }
        }
        
        stage('Execute JMeter Tests') {
            steps {
                // Execute JMeter performance tests
                sh 'jmeter -n -t src/main/resources/MedHeadPerfTest.jmx'
            }
        }
        
        stage('Stop App') {
            steps {
                // Stop the Spring application
                sh 'kill $(lsof -t -i:29001)'
            }
        }
    }
	post {
		success{
			when {
				expression {
					BRANCH_NAME == 'dev'
				}
			}
			steps{
				sh 'git merge -X master'
			}
		}
	}
}