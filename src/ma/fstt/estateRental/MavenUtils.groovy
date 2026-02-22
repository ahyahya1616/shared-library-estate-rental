package ma.fstt.estateRental

class MavenUtils {

    def script

    MavenUtils(script) {
        this.script = script
    }

    def build() {
        script.echo "Build Maven en cours..."
        script.sh """
            mvn clean package -DskipTests
        """
    }
}