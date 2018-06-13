import javafx.application.Application
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {

        primaryStage.title = "Synthesizer"
        val layout = StackPane()
        primaryStage.scene = AppScene(layout)
        primaryStage.show()

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}



