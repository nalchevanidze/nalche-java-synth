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
    VolumeEnvelope volume  = new VolumeEnvelope(8);

    public float next(){

        state = (state + stepSize) % 1;

        return (float)(Wave.saw(state) * 125f * volume.next());
    }

    SoundEvent(double step_size){
        stepSize = step_size;
    }
}


public class SoundProcessor {

    private SourceDataLine _dataLine;

    private boolean isPlaying = false;
    private byte[] _buffer;
    private int _processingIndex = 0;
    private SoundEvent[] _events = new SoundEvent[1];
    private int [] _indexes = new int[0];

    public SoundProcessor() {
        AudioFormat audioFormat = new AudioFormat(Sample.rate, 32, 1, true, false);
        _buffer = new byte[Sample.rate];
        Thread initThread = new Thread(() -> {
            try {
                _dataLine = AudioSystem.getSourceDataLine(audioFormat);
                _dataLine.open(audioFormat, Sample.rate);
                _dataLine.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        });
        initThread.start();
    }

    private float noteToFrequency(int index) {
        float power = ((float) index - 49) / 12;
        return (float) Math.pow(2, power) * 440;
    }

    private byte singleWave() {
        if (!isPlaying || _events[0] == null) return (byte) 0;
        double value = 0;
        for( SoundEvent event :_events){
            value += event.next();
        }
        value = value / (_events.length +2);
        return (byte) value;

    }

    public void next() {
        if (isPlaying) {
            int processSize = 100;
            for (int i = 0; i < processSize; i++) {
                _processingIndex++;
                _buffer[_processingIndex] = singleWave();
                if (_processingIndex >= Sample.rate - 1) {
                    _dataLine.write(_buffer, 0, Sample.rate);
                    _processingIndex = 0;
                }
            }
        } else if (_dataLine != null) {
            _dataLine.drain();
        }
    }

    public void play(int[] indexes) {

        if(_indexes == indexes) return;
        _indexes = indexes;


        _events = new SoundEvent[indexes.length];
        for(int i = 0; i < indexes.length; i++){
            float frequency = noteToFrequency(indexes[i] - 12);
            _events[i] = new SoundEvent(frequency / Sample.rate);
        }
        isPlaying = true;

    }

    public void stop() {
        _events = new SoundEvent[0];
        isPlaying = false;
    }

    public void terminate() {
        _dataLine.drain();
        _dataLine.close();

    }

}
