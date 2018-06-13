package sound

import javax.sound.sampled.AudioFormat

object Sample {
    const val rate = 1280000
    private const val quality = 16
    val audioFormat = AudioFormat(rate.toFloat(), quality, 1, true, false)
}