package sound

class SoundManager {

    private val activeSoundProcessors = arrayOfNulls<SoundProcessor>(size = 49)

    fun add(note: Int) {

        if (note < 0 || note > 49) return

        if (activeSoundProcessors[note] != null) {
            activeSoundProcessors[note]!!.stop()
        }
        activeSoundProcessors[note] = SoundProcessor(note)
    }

    fun remove(note: Int) {

        if (note < 0 || note > 49) return

        if (activeSoundProcessors[note] != null) {
            activeSoundProcessors[note]!!.stop()
            activeSoundProcessors[note] = null
        }
    }
}