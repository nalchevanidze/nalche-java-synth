package sound

class SoundManager {

    private val soundProcesses = arrayOfNulls<SoundProcessor>(size = 49)

    fun add(note: Int) {

        if (note < 0 || note > 49) return

        if (soundProcesses[note] != null) {
            soundProcesses[note]!!.stop()
        }
        soundProcesses[note] = SoundProcessor(note)
    }

    fun remove(note: Int) {

        if (note < 0 || note > 49) return

        if (soundProcesses[note] != null) {
            soundProcesses[note]!!.stop()
            soundProcesses[note] = null
        }
    }
}