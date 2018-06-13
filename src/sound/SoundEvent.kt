package sound

internal class Envelope(time: Double) {

    private var state = 1000.0
    private var stepSize = 0.0

    init {
        stepSize = state / (time * Sample.rate)
    }

    operator fun next(): Double {
        if (state <= 0) return 0.0
        state -= stepSize
        return if (state <= 0) 0.0 else state / 10
    }
}

internal class SoundEvent(note: Int) {
    private var state = 0.0
    private var stepSize = 1.0
    private var isPressed = true
    var finished = false
    private val volume = Envelope(1.4)

    fun stop() {
        isPressed = false
    }

    init {
        stepSize = (600 * toFrequency(note) / Sample.rate).toDouble()
    }

    private fun toFrequency(index: Int): Float {
        val power = (index.toFloat() - 49) / 12
        return Math.pow(2.0, power.toDouble()).toFloat() * 440
    }

    operator fun next(): Byte {
        state = (state + stepSize) % 360

        var volumeValue = 100.0

        if (!isPressed) {
            volumeValue = this.volume.next()
        }

        if (volumeValue < 1) {
            finished = true
            return 0
        }

        return (saw(state) * volumeValue).toByte()
    }


}