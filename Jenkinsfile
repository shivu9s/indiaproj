pipeline {
  agent any

  tools {
    jdk 'Java21'          
    maven 'Maven'
  }

  environment {
    DOCKERHUB_USER = "shivu9s"     
    IMAGE_NAME = "shivu9s/indiaproj"
    IMAGE_TAG = "1.0"
  }

  stages {

    stage('Checkout Code') {
      steps {
        echo 'Pulling from Github'
        git branch: 'main',
            credentialsId: 'git.cred',
            url: 'https://github.com/shivu9s/indiaproj.git'
      }
    }

    stage('Build Project') {
      steps {
        echo 'Building Spring Boot project'
        bat '''
          mvn clean package -DskipTests
        '''
      }
    }

    stage('Build Docker Image') {
      steps {
        echo 'Building Docker Image'
        bat '''
          docker build -t %IMAGE_NAME%:%IMAGE_TAG% .
        '''
      }
    }

    stage('Push Docker Image to DockerHub') {
      steps {
        echo 'Pushing Docker Image'
        withCredentials([
          usernamePassword(
            credentialsId: 'dockerhubpwd',
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
          )
        ]) {
          bat '''
            echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
            docker push %IMAGE_NAME%:%IMAGE_TAG%
          '''
        }
      }
    }

    stage('Deploy to Minikube (K8s)') {
      steps {
        echo 'Deploying to Kubernetes'
        bat '''
          minikube start
          minikube image load %IMAGE_NAME%:%IMAGE_TAG%

          kubectl apply -f deployment.yaml
          kubectl apply -f services.yaml

          kubectl get pods
          kubectl get svc

          minikube addons enable dashboard
        '''
      }
    }
  }

  post {
    success {
      echo 'BUILD + DOCKER + K8s DEPLOY SUCCESSFUL!'
    }
    failure {
      echo 'OOPS!!! Failure.'
    }
  }
}
