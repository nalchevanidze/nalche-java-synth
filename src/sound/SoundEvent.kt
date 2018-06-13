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
        stepSize = 360.0 * toFrequency(note) / Sample.rate
    }

    private fun toFrequency(index: Int) = Math.pow(2.0, (index - 49.0) / 12) * 440

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

        return (sine(state) * volumeValue).toByte()
    }


}