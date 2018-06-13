package panel

import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class Keyboard : Canvas() {
    private val _gc: GraphicsContext
    private val _height: Int = 180

    init {
        height = 185.0
        width = 720.0
        _gc = graphicsContext2D
        for (i in 0..24) {
            renderNote(i, false)
        }

    }

    fun renderNote(i: Int, isActive: Boolean) {
        _gc.fill = Color.GRAY
        _gc.fillRect((i * 30).toDouble(), 0.0, 30.0, 185.0)
        var h = _height;
        if(isActive){
            h -= 20
        }
        val keyId = ((i + 1) % 12)

        when (keyId) {
            2, 4, 7, 9, 11 -> {
                _gc.fill = Color.BLACK
                _gc.fillRect((i * 30).toDouble(), 0.0, 30.0, h.toDouble())
            }
            else -> {
                _gc.fill = Color.WHITE
                _gc.fillRect((i * 30 + 1).toDouble(), 0.0, (30 - 1).toDouble(), h.toDouble())
            }
        }
    }
}
