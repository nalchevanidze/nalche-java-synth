package sound
import javax.sound.sampled.AudioSystem.getSourceDataLine
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.SourceDataLine

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
