package sound;

import com.sun.istack.internal.NotNull;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

final class Sample {
    public static int rate = 8 * 1024;
    public static int quality = 32;
    public static AudioFormat audioFormat = new AudioFormat(rate, quality , 1, true, false);
}

final class Envelope {
    private double state = 1;
    private double stepSize = 0;

    Envelope(float time ){
        stepSize = 1.0f / (Sample.rate * time);
    }

    public double next(){
        if(state == 0) return 0;
        state -= stepSize;
        if(state < 0){
            state = 0;
        }
        return state;
    }
}


final class SoundEvent {
    private double state = 0;
    private double stepSize = 1;
    private boolean isPressed = true;
    public boolean finished = false;
    private @NotNull  Envelope volume  = new Envelope(1.4f);

    public void stop(){
        isPressed = false;
    }

    SoundEvent(int note){
        stepSize = toFrequency(note) / Sample.rate;
    }

    private float toFrequency(int index) {
        float power = ((float) index - 49) / 12;
        return (float) Math.pow(2, power) * 440;
    }

    public byte next(){

        state = (state + stepSize) % 1;

        double v = isPressed? 1 : volume.next();

        if(v == 0){
            finished = true;
            return 0;
        }

        return (byte)(Wave.square(state) * v * 30f );
    }


}


final public class SoundProcessor {

    private @NotNull SourceDataLine _dataLine;
    private @NotNull SoundEvent _soundEvent;

    public SoundProcessor(int i) {
        _soundEvent = new SoundEvent(i);
        Thread initThread = new Thread(() -> {
            try {

                //create
                _dataLine = AudioSystem.getSourceDataLine(Sample.audioFormat);
                _dataLine.open(Sample.audioFormat, Sample.rate);
                _dataLine.start();

                //loop
                while (!_soundEvent.finished) {
                    next();
                    Thread.sleep(1);
                }

                // finish
                _dataLine.drain();
                _dataLine.close();


            } catch (LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }

        });
        initThread.start();
    }

    private void next(){
        if (!_soundEvent.finished) {
            byte[] buffer = new byte[Sample.rate];
            for (int i = 0; i < Sample.rate; i++) {
                buffer[i] =  _soundEvent.next();
            }
             _dataLine.write(buffer, 0, Sample.rate);
        }
    }

    public void stop(){
        _soundEvent.stop();
    }
}
