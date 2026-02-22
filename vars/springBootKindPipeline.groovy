def call(Map config) {

    pipeline {

        agent {
            label 'docker-kind'
        }

        environment {
            SERVICE_NAME = config.serviceName
            IMAGE_NAME   = config.imageName
            JAR_NAME     = config.jarName
            PORT         = config.port

            BUILD_TAG    = "${env.BUILD_NUMBER}"
        }

        tools {
            maven 'maven-3.9'
            jdk 'jdk-17'
        }

        stages {

            stage('Checkout Source Code') {
                steps {
                    checkout scm
                }
            }

            stage('Build Application (Maven)') {
                steps {
                    script {
                        def maven = new ma.fstt.estateRental.MavenUtils(this)
                        maven.build()
                    }
                }
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        def docker = new ma.fstt.estateRental.DockerUtils(this)
                        docker.buildImage(IMAGE_NAME, BUILD_TAG)
                    }
                }
            }

            stage('Load Image into KIND') {
                steps {
                    script {
                        def kind = new ma.fstt.estateRental.KindUtils(this)
                        kind.loadImage(IMAGE_NAME, BUILD_TAG)
                    }
                }
            }
        }

        post {
            success {
                echo "Pipeline SUCCESS pour ${SERVICE_NAME} (version ${BUILD_TAG})"
            }
            failure {
                echo "Pipeline FAILED pour ${SERVICE_NAME}"
            }
        }
    }
}