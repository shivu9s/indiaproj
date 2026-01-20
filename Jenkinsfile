pipeline {
  agent any
  tools {
    jdk 'Java17'
    maven 'Maven'
  }
  stages {
    stage('Checkout Code') {
      steps {
        echo 'Pulling from Github'
        git branch: 'main', credentialsId: 'git.cred', url: 'https://github.com/shivu9s/indiaproj.git'
      }
    }
    stage('Test Code') {
      steps {
        echo 'JUNIT Test case execution started'
        bat 'mvn clean test'
        
      }
      post {
        always {
		  junit '**/target/surefire-reports/*.xml'
          echo 'Test Run is SUCCESSFUL!'
        }

      }
    }
    stage('Build Project') {
      steps {
        echo 'Building Java project'
        bat 'mvn clean package -DskipTests'
      }
    }
    stage('Build the Docker Image') {
      steps {
        echo 'Building Docker Image'
        bat 'docker build -t myindiaproj:1.0 .'
      }
    }
    stage('Push Docker Image to DockerHub') {
      steps {
        echo 'Pushing  Docker Image'
        withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'DOCKER_PASS')]) {
  	      bat '''
          eecho %DOCKER_PASS% | docker login -u shivu9s --password-root@4567
    docker tag myjavaproj:1.0 shivu9s/myindiaproj:1.0
    docker push shivu9s/myindiaproj:1.0
          '''}
      }
    }
   stage('Deploy to Minikube (K8s)') {
      steps {
        echo 'Deploying to Kubernetes'
        sh '''
          minikube start
          minikube image load ${IMAGE_NAME}:${IMAGE_TAG}

          kubectl apply -f deployment.yaml
          kubectl apply -f services.yaml

          kubectl get pods
          kubectl get svc
          minikube addons enable dashboard
          minikube dashboard
        '''
      }
    }
  }
ye stage add kr lo apne m
  }
  post {
    success {
      echo 'BUild and Run is SUCCESSFUL!'
    }
    failure {
      echo 'OOPS!!! Failure.'
    }
  }
}
