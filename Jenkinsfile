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
                sh 'mvn verify'
//                 sh 'mvn verify -Dmaven.test.failure.ignore=true'
                //save tests results for use outside container
                stash name: 'allure-results', includes: 'allure-results/*'
            }
       }
    }
    post {
        //generate allure report
        always {
            unstash 'allure-results'
            allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
        }
    }
}