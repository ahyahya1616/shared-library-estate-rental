package ma.fstt.estateRental

class KindUtils {

    def script

    KindUtils(script) {
        this.script = script
    }

    def loadImage(String imageName, String tag) {
        script.echo "Chargement de l'image ${imageName}:${tag} dans KIND"

        script.sh """
            kind load docker-image ${imageName}:${tag}
        """
    }
}