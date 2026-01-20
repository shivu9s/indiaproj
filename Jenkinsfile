pipeline {
  agent any

  tools {
    jdk 'Java17'
    maven 'Maven'
  }

  environment {
    IMAGE_NAME = "shivu9s/myindiaproj"
    IMAGE_TAG  = "1.0"
  }

  stages {

    stage('Checkout Code') {
      steps {
        echo 'Pulling code from GitHub'
        git branch: 'main',
            credentialsId: 'git.cred',
            url: 'https://github.com/shivu9s/indiaproj.git'
      }
    }

    stage('Test Code') {
      steps {
        echo 'Running JUnit tests'
        bat 'mvn clean test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('Build Project') {
      steps {
        echo 'Building Java project'
        bat 'mvn clean package -DskipTests'
      }
    }

    stage('Build Docker Image') {
      steps {
        echo 'Building Docker image'
        bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
      }
    }

    stage('Push Docker Image to DockerHub') {
      steps {
        echo 'Pushing Docker image to DockerHub'
        withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'DOCKER_PASS')]) {
          bat '''
          echo %DOCKER_PASS% | docker login -u shivu9s --password-stdin
          docker push %IMAGE_NAME%:%IMAGE_TAG%
          '''
        }
      }
    }

    stage('Deploy to Minikube (K8s)') {
      steps {
        echo 'Deploying to Kubernetes (Minikube)'
        bat '''
        minikube start
        kubectl apply -f deployment.yaml
        kubectl apply -f services.yaml
        kubectl get pods
        kubectl get svc
        '''
      }
    }
  }

  post {
    success {
      echo 'BUILD & DEPLOYMENT SUCCESSFUL 🎉'
    }
    failure {
      echo 'BUILD FAILED ❌'
    }
  }
}
