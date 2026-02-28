package ma.fstt.estateRental

class KindUtils {

    def script
    // Définissez le nom du cluster ici ou passez-le en paramètre
    def CLUSTER_NAME = 'estate-rental'

    KindUtils(script) {
        this.script = script
    }

    def loadImage(String imageName, String tag) {
        script.echo "Chargement de ${imageName}:${tag} dans KIND (${CLUSTER_NAME})"

        // 1. Charger l'image dans le bon cluster
        script.sh "kind load docker-image ${imageName}:${tag} --name ${CLUSTER_NAME}"

        // 2. Déployer via Helm avec le namespace dev
        script.sh """
            helm upgrade --install ${imageName} chart/ \
            --namespace dev \
            --create-namespace \
            --set image.tag=${tag}
        """
    }
}