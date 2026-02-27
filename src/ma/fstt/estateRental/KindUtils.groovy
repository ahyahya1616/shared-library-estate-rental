package ma.fstt.estateRental

class KindUtils {

    def script

    KindUtils(script) {
        this.script = script
    }

    def loadImage(String imageName, String tag) {
        script.echo "Chargement de l'image ${imageName}:${tag} dans KIND et déploiement avec Helm"

        // Étape 1 : charger l'image locale dans Kind
        script.sh """
            kind load docker-image ${imageName}:${tag}
        """

        // Étape 2 : déployer via Helm sur Kind
        script.sh """
            helm upgrade --install ${imageName} chart/ --set image.tag=${tag}
        """
    }
}