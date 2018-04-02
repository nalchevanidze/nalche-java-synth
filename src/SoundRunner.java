import sound.SoundProcessor;

public class SoundRunner extends Thread {

    private SoundProcessor _sound;

    public SoundRunner(SoundProcessor sound) {
        _sound = sound;
    }

    public void run() {
        try {
            while (true) {
                _sound.next();
                Thread.sleep(120);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
