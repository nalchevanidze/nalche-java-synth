package sound
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem.getSourceDataLine
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.SourceDataLine


internal object Sample {
    const val rate = 1280000
    private const val quality = 16
    val audioFormat = AudioFormat(rate.toFloat(), quality, 1, true, false)
}

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

        return (sine(state) * volumeValue).toByte()
    }


}


class SoundProcessor(i: Int) {

    private var _dataLine: SourceDataLine = getSourceDataLine(Sample.audioFormat)
    private val _soundEvent: SoundEvent = SoundEvent(i)

    init {
        val initThread = Thread {
            try {

                //create
                _dataLine.open(Sample.audioFormat, Sample.rate)
                _dataLine.start()

                //loop
                while (!_soundEvent.finished) {
                    next()
                    Thread.sleep(1)
                }

                // finish
                _dataLine.drain()
                _dataLine.close()


            } catch (e: LineUnavailableException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }


        }
        initThread.start()
    }

    private operator fun next() {

        val buffer = ByteArray(Sample.rate)
        for (i in 0 until Sample.rate) {
            buffer[i] = _soundEvent.next()
        }
        _dataLine.write(buffer, 0, Sample.rate)

    }

    fun stop() {
        _soundEvent.stop()
    }
}
