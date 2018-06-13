import javafx.scene.Scene
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import panel.Keyboard
import panel.Note
import sound.SoundManager

class AppScene (layout: StackPane): Scene(layout, 720.0, 190.0) {

    private val _keyboard = Keyboard()
    private val _noteRegistry = Note()
    private val _soundRegistry = SoundManager()

    private fun addNote (note: Int ){
        _keyboard.renderNote(note, true)
        _soundRegistry.add(note)
    }

    private fun removeNote (note: Int){
        _keyboard.renderNote(note, false)
        _soundRegistry.remove(note)
    }

    init{
        layout.children.add(_keyboard)

        setOnMousePressed { event: MouseEvent ->
            val note = _noteRegistry.fromMouse(event)
            if (!_noteRegistry.notes.contains(note)) {
                _noteRegistry.onClick(event)
                addNote(note)
            }
        }

        setOnMouseReleased { event: MouseEvent ->
            val note = _noteRegistry.fromMouse(event)
            _noteRegistry.onRelease(event)
            removeNote(note)
        }

        setOnKeyPressed { event: KeyEvent ->
            val note = _noteRegistry.noteFromKeyEvent(event)
            if (!_noteRegistry.notes.contains(note)) {
                _noteRegistry.keyPress(event)
                addNote(note)
            }
        }

        setOnKeyReleased { event: KeyEvent ->
            val note = _noteRegistry.noteFromKeyEvent(event)
            _noteRegistry.keyRelease(event)
            removeNote(note)
        }
    }

}