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

    fun fromMouse(event: MouseEvent) = event.sceneX.toInt() / 30

    fun noteFromKeyEvent(event: KeyEvent): Int {
        val key = event.code.getName().toLowerCase()[0]
        return when (key) {
            'y' ->  1
            's' ->  2
            'x' ->  3
            'd' ->  4
            'c' ->  5
            'v' ->  6
            'g' ->  7
            'b' ->  8
            'h' ->  9
            'n' ->  10
            'j' ->  11
            'm' ->  12
            'q' ->  13
            '2' ->  14
            'w' ->  15
            '3' ->  16
            'e' ->  17
            'r' ->  18
            '5' ->  19
            't' ->  20
            '6' ->  21
            'z' ->  22
            '7' ->  23
            'u' ->  24
            else -> -1
        }
    }
}
