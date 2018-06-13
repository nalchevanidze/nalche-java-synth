package sound

fun sine(waveIndex: Double) = Math.sin(Math.toRadians(waveIndex))

fun saw(waveIndex: Double) = waveIndex / 180 - 1

fun square(waveIndex: Double) = Math.round(waveIndex/360)

fun mixed(i: Double) = (square(i) / 2 + saw(i) / 2 + sine(i)) / 3

