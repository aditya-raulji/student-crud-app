pipeline {
    agent any
    tools {
        maven 'Maven'
        // jdk line HATA DO - Jenkins already Java use kar raha hai
    }
    stages {
        stage('Pull Code from GitHub') {
            steps {
                checkout scm
                echo 'Code pulled successfully.'
            }
        }
        stage('Build with Maven') {
            steps {
                echo 'Building the project...'
                script {
                    if (isUnix()) {
                        sh 'mvn clean compile'
                    } else {
                        bat 'mvn clean compile'
                    }
                }
            }
        }
        stage('Run Test Cases') {
            steps {
                echo 'Running JUnit test cases...'
                script {
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
        }
        stage('Generate Build Artifact (.jar)') {
            steps {
                echo 'Generating executable JAR...'
                script {
                    if (isUnix()) {
                        sh 'mvn package -DskipTests'
                    } else {
                        bat 'mvn package -DskipTests'
                    }
                }
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }
    post {
        success { echo '✅ BUILD SUCCESS!' }
        failure { echo '❌ BUILD FAILED!' }
    }
}
