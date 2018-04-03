package sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

class Sample {
    public static int rate = 8 * 1024;
}

class VolumeEnvelope {
    float state = 1;
    float stepSize = 0;
    VolumeEnvelope(float time ){
        stepSize = 1.0f / (Sample.rate * time);
    }
    public float next(){
        if(state == 0) return 0;
        state -= stepSize;
        if(state < 0){
            state = 0;
        }
        return (float) Math.pow((double)state,2.0);
    }
}


class SoundEvent {
    double state = 0;
    double stepSize = 1;
    VolumeEnvelope volume  = new VolumeEnvelope(3);
    boolean finished = false;

    SoundEvent(int note){
        stepSize = toFrequency(note) / Sample.rate;
    }

    private float toFrequency(int index) {
        float power = ((float) index - 49) / 12;
        return (float) Math.pow(2, power) * 440;
    }

    public float next(){

        state = (state + stepSize) % 1;
        float v = volume.next();
        if(v == 0){
            finished = true;
            return 0;
        }

        return (float)(Wave.saw(state) * 125f * v);
    }


}


public class SoundProcessor {

    public boolean isPlaying = true;
    private SourceDataLine _dataLine;
    private byte[] _buffer;
    private SoundEvent se;

    public SoundProcessor(int i) {
        se = new SoundEvent( i + 12);
        AudioFormat audioFormat = new AudioFormat(Sample.rate, 16, 1, true, false);
        _buffer = new byte[Sample.rate];

        Thread initThread = new Thread(() -> {
            try {
                //create dataline
                _dataLine = AudioSystem.getSourceDataLine(audioFormat);
                _dataLine.open(audioFormat, Sample.rate);
                _dataLine.start();

            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

            try {
                while (isPlaying) {
                    next();
                    Thread.sleep(100);
                }
                end();
                System.out.println("terminate");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        initThread.start();
    }

    public void next(){
        if (_dataLine == null) return;
        if (!se.finished) {
            for (int i = 0; i < Sample.rate; i++) {
                _buffer[i] = (byte) (se.next() / 2);
            }
             _dataLine.write(_buffer, 0, Sample.rate);
        }else{
            isPlaying = false;
        }
    }

    public void stop() {
        isPlaying = false;
    }

    public void end() {
        _dataLine.drain();
        _dataLine.close();
    }

}
