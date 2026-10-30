pipeline {
    agent any

    environment {
        IMAGE_NAME = 'kuat-akberen-student-api'
        IMAGE_TAG  = 'v1.0-kuat-akberen'
        STUDENT    = 'Kuat Akberen'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "=== ${env.STUDENT} Student API Pipeline ==="
                echo "Checking out source code from GitHub..."
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "Building Spring Boot application for ${env.STUDENT}..."
                sh './gradlew bootJar --no-daemon'
            }
        }

        stage('Test') {
            steps {
                echo "Running automated tests for ${env.STUDENT}..."
                sh './gradlew test --no-daemon'
            }
            post {
                always {
                    junit '**/build/test-results/test/*.xml'
                }
                failure {
                    echo "Tests FAILED for ${env.STUDENT} — stopping pipeline."
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker image: ${env.IMAGE_NAME}:${env.IMAGE_TAG}..."
                sh "docker build -t ${env.IMAGE_NAME}:${env.IMAGE_TAG} ."
            }
        }

        stage('Verify') {
            steps {
                echo "Verifying image ${env.IMAGE_NAME}:${env.IMAGE_TAG} exists..."
                sh "docker image inspect ${env.IMAGE_NAME}:${env.IMAGE_TAG} --format 'Image OK: {{.Id}} created {{.Created}} size {{.Size}} bytes'"
                sh "docker images ${env.IMAGE_NAME}:${env.IMAGE_TAG}"
                echo "Pipeline completed successfully for ${env.STUDENT}!"
            }
        }
    }

    post {
        success {
            echo "SUCCESS: ${env.STUDENT} pipeline finished. Image: ${env.IMAGE_NAME}:${env.IMAGE_TAG}"
        }
        failure {
            echo "FAILURE: ${env.STUDENT} pipeline failed. Check console output above."
        }
    }
}
