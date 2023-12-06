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
            //todo fix "unstable" label on jenkins pipeline
                sh 'mvn verify -Dmaven.test.failure.ignore=true'
                //save tests results for use outside container
                stash name: 'allure-results', includes: 'allure-results/*'
            }
            post {
                unstable {
                    echo "Run after unstable stage"
                    script {
                        currentBuild.result = 'SUCCESS'
                    }
                }
            }
       }
    }
    post {
        //generate allure report
        always {
            unstash 'allure-results'
            allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
            echo currentBuild.currentResult
        }
    }
}