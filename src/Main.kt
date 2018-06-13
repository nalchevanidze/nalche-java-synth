import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import panel.Keyboard
import panel.Note
import sound.SoundProcessor

internal class SoundManager {

    private val activeSoundProcessors = arrayOfNulls<SoundProcessor>(size = 49)

    fun add(note: Int) {

        if (note < 0 || note > 49) return

        if (activeSoundProcessors[note] != null) {
            activeSoundProcessors[note]!!.stop()
        }
        activeSoundProcessors[note] = SoundProcessor(note)
    }

    fun remove(note: Int) {

        if (note < 0 || note > 49) return

        if (activeSoundProcessors[note] != null) {
            activeSoundProcessors[note]!!.stop()
            activeSoundProcessors[note] = null
        }
    }
}


class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {

        primaryStage.title = "Synthesizer"
        val layout = StackPane()
        val can = Keyboard()
        layout.children.add(can)

        val scene = Scene(layout, 720.0, 190.0)
        primaryStage.scene = scene
        primaryStage.show()

        val notes = Note()
        val sounds = SoundManager()

        fun addNote (note: Int ){
            can.renderNote(note, true)
            sounds.add(note)
        }

        fun removeNote (note: Int){
            can.renderNote(note, false)
            sounds.remove(note)
        }

        can.setOnMousePressed { event: MouseEvent ->
            val note = notes.fromMouse(event)
            if (!notes.notes.contains(note)) {
                notes.onClick(event)
                addNote(note)
            }
        }
        can.setOnMouseReleased { event: MouseEvent ->
            val note = notes.fromMouse(event)
            notes.onRelease(event)
            removeNote(note)
        }

        scene.setOnKeyPressed { event: KeyEvent ->
            val note = notes.noteFromKeyEvent(event)
            if (!notes.notes.contains(note)) {
                notes.keyPress(event)
                addNote(note)
            }
        }
        scene.setOnKeyReleased { event: KeyEvent ->
            val note = notes.noteFromKeyEvent(event)
            notes.keyRelease(event)
            removeNote(note)
        }

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}



