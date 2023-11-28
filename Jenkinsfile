pipeline {
    agent any
    stages {
        stage('Run tests'){
            agent {
                docker {
                    image 'maven:3.9.5-eclipse-temurin-17-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                //todo research test results dir(its updated?)
                sh 'mvn verify'
            }
       }
    }
    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
        }
    }
}