// vars/deployMicroservice.groovy
def call(String serviceName) {
    echo "Building Docker image for ${serviceName}..."
    sh "docker build -t ${serviceName}:latest ."

    echo "Docker image ${serviceName}:latest is ready locally"
    // If in future you push to a registry:
    // sh "docker tag ${serviceName}:latest myregistry/${serviceName}:latest"
    // sh "docker push myregistry/${serviceName}:latest"
}