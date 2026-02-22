package ma.fstt.estateRental

class DockerUtils {

    def script

    DockerUtils(script) {
        this.script = script
    }

    def buildImage(String imageName, String tag) {
        script.echo "Construction de l'image Docker : ${imageName}:${tag}"

        script.sh """
            docker build -t ${imageName}:${tag} .
            docker tag ${imageName}:${tag} ${imageName}:latest
        """
    }
}