package ma.fstt.estateRental

class DockerUtils {

    def script

    DockerUtils(script) {
        this.script = script
    }

    def buildImage(String imageName, String tag) {
        script.echo "Construction de l'image Docker : ${imageName}:${tag}"

        script.sh """
            docker build --pull -t ${imageName}:${tag} .
            docker tag ${imageName}:${tag} ${imageName}:latest
        """

        //  Nettoyage immédiat des images <none> (dangling) créées par ce build
        script.echo "Nettoyage des images orphelines..."
        script.sh "docker image prune -f"

        script.echo "Construction terminée avec succès."
    }
}