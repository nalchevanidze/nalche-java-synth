package panel

import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import java.util.HashSet

class Note {

    val notes: MutableSet<Int> = HashSet()

    fun onClick(event: MouseEvent) {
        notes.add(fromMouse(event))
    }

    fun onRelease(event: MouseEvent) {
        notes.remove(fromMouse(event))
    }

    fun keyPress(event: KeyEvent) {
        notes.add(noteFromKeyEvent(event))
    }

    fun keyRelease(event: KeyEvent) {
        notes.remove(noteFromKeyEvent(event))
    }

    fun fromMouse(event: MouseEvent) = (event.sceneX / 30).toInt()

    fun noteFromKeyEvent(event: KeyEvent): Int {
        val key = event.code.getName().toLowerCase()[0]

        when (key) {
            'y' -> return 1
            's' -> return 2
            'x' -> return 3
            'd' -> return 4
            'c' -> return 5
            'v' -> return 6
            'g' -> return 7
            'b' -> return 8
            'h' -> return 9
            'n' -> return 10
            'j' -> return 11
            'm' -> return 12
            'q' -> return 13
            '2' -> return 14
            'w' -> return 15
            '3' -> return 16
            'e' -> return 17
            'r' -> return 18
            '5' -> return 19
            't' -> return 20
            '6' -> return 21
            'z' -> return 22
            '7' -> return 23
            'u' -> return 24
        }
        return -1
    }
}
