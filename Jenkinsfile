pipeline {
    agent any

    tools {
        // Assumes Maven is configured in Jenkins under Global Tool Configuration with this name
        maven 'Maven'
        jdk 'JDK 21' // Assumes JDK 21 is configured in Jenkins
    }

    stages {
        stage('Pull Code from GitHub') {
            steps {
                // Checkout code from the implicitly linked SCM repo (like GitHub plugin)
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
                    // Archives the generated JAR file so it is available from the Jenkins build page
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }
}
