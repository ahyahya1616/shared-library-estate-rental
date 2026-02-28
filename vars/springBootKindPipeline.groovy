def call(Map config) {

    if (!config.serviceName || !config.imageName) {
        error "serviceName et imageName sont obligatoires"
    }

    pipeline {

        agent {
            docker {
                image 'ci-tools:latest'
                args '-v /var/run/docker.sock:/var/run/docker.sock'
                reuseNode true
                alwaysPull true
            }
        }

        stages {

            stage('Init') {
                steps {
                    script {
                        env.SERVICE_NAME = config.serviceName
                        env.IMAGE_NAME   = config.imageName
                        env.BUILD_TAG    = env.BUILD_NUMBER
                    }
                }
            }

            stage('Checkout Source Code') {
                steps {
                    checkout scm
                }
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        def dockerUtils = new ma.fstt.estateRental.DockerUtils(this)
                        dockerUtils.buildImage(IMAGE_NAME, BUILD_TAG)
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
                // Utilisez env.SERVICE_NAME et env.BUILD_TAG
                echo "Pipeline SUCCESS pour ${env.SERVICE_NAME} (version ${env.BUILD_TAG})"
            }
            failure {
                // Même chose ici pour éviter l'erreur
                echo "Pipeline FAILED pour ${env.SERVICE_NAME}"
            }
        }
    }
}