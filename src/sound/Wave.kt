package sound


fun sine(waveIndex: Double): Double {
        val i = Math.toRadians(waveIndex)
        return Math.sin(i)
}

fun saw(waveIndex: Double): Double {
        return waveIndex / 180 - 1
}

fun square(waveIndex: Double): Double {
        return (if (waveIndex > 180) -1 else 1).toDouble()
}

fun mixed(i: Double): Double {
    return (square(i) / 2 + saw(i) / 2 + sine(i)) / 3
}
