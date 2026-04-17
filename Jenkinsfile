pipeline {
    agent any

    environment {
        // 1. CHANGE THIS to your actual Docker Hub username
        DOCKER_HUB_USER = 'your_dockerhub_username'
        // 2. The name of your repository on Docker Hub
        IMAGE_NAME = 'hotel-booking-system'
    }

    stages {
        stage('Checkout SCM') {
            steps {
                // Pulls code from GitHub using the credentials you set up
                checkout scm: [
                    $class: 'GitSCM', 
                    userRemoteConfigs: [[
                        url: "https://github.com/${DOCKER_HUB_USER}/hotel-booking-system.git", 
                        credentialsId: 'github-credentials'
                    ]], 
                    branches: [[name: '*/main']]
                ]
            }
        }

        stage('Maven Build') {
            steps {
                // Compiles the Java code and creates the .jar file
                // Use 'bat' because you are on Windows 11
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                // This block uses the 'dockerhub-credentials' ID you created in Jenkins
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', 
                                 passwordVariable: 'DOCKER_HUB_PW', 
                                 usernameVariable: 'DOCKER_HUB_ID')]) {
                    
                    echo "Building Docker Image..."
                    bat "docker build -t %DOCKER_HUB_USER%/%IMAGE_NAME%:latest ."

                    echo "Logging into Docker Hub..."
                    // Passwords are passed securely via %Variable% in Windows cmd
                    bat "echo %DOCKER_HUB_PW% | docker login -u %DOCKER_HUB_ID% --password-stdin"

                    echo "Pushing Image..."
                    bat "docker push %DOCKER_HUB_USER%/%IMAGE_NAME%:latest"
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the logs.'
        }
    }
}