pipeline {
    agent any

    environment {
        DOCKER_HUB_USER = 'arpitbaiju'
        IMAGE_NAME = 'hotel-booking-system'
    }

    stages {
        // Jenkins automatically checks out code here, no stage needed

        stage('Maven Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', 
                                 passwordVariable: 'DOCKER_HUB_PW', 
                                 usernameVariable: 'DOCKER_HUB_ID')]) {
                    
                    echo "Building Image..."
                    bat "docker build -t %DOCKER_HUB_USER%/%IMAGE_NAME%:latest ."

                    echo "Login to Docker Hub..."
                    bat "echo %DOCKER_HUB_PW% | docker login -u %DOCKER_HUB_ID% --password-stdin"

                    echo "Pushing Image..."
                    bat "docker push %DOCKER_HUB_USER%/%IMAGE_NAME%:latest"
                }
            }
        }
    }

    post {
        success { echo 'Pipeline completed successfully!' }
        failure { echo 'Pipeline failed. Check the logs.' }
    }
}