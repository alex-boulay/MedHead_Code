pipeline {
    agent any

    stages {        
        stage('Build') {
            steps {
                echo 'Building'
                bat 'mvn clean install'
            }
        }
        
        stage('Start App') {
            steps {
                echo 'Starting the Spring app'
                bat 'start java -jar target/MedHead.jar'
            }
        }
        
        stage('Run Postman Collection') {
            steps {
                // Run Postman collection using Newman
                bat 'newman.cmd run ./P11_MedHead.postman_collection.json'
            }
        }
        
        stage('Execute JMeter Tests') {
            steps {
                // Execute JMeter performance tests
                bat 'jmeter -n -t src/main/resources/MedHeadPerfTest.jmx'
            }
        }
        
        stage('Stop App') {
            steps {
                // Stop the Spring application
                bat 'taskkill /F /FI "PID gt 0" /IM java.exe'
            }
        }
    }
    
    post {
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