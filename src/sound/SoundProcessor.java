package sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

class SoundEvent {
    double state = 0;
    double stepSize = 1;
    SoundEvent(double step_size){
        stepSize = step_size;
    }
}


public class SoundProcessor {

    private SourceDataLine _dataLine;
    private int _sampleRate = 8 * 1024;
    private boolean isPlaying = false;
    private byte[] _buffer;
    private int _processingIndex = 0;
    private SoundEvent[] _events = new SoundEvent[1];

    public SoundProcessor() {
        AudioFormat audioFormat = new AudioFormat(_sampleRate, 32, 1, true, false);
        _buffer = new byte[_sampleRate];
        Thread initThread = new Thread(() -> {
            try {
                _dataLine = AudioSystem.getSourceDataLine(audioFormat);
                _dataLine.open(audioFormat, _sampleRate);
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
        for( SoundEvent e :_events){
            e.state = (e.state + e.stepSize) % 1;
            value += (Wave.saw(e.state) * 125f);
        }
        value = value / (_events.length +2);
        return (byte) value;

    }

    public void next() {
        if (isPlaying) {
            int processSize = 250;
            for (int i = 0; i < processSize; i++) {
                _processingIndex++;
                _buffer[_processingIndex] = singleWave();
                if (_processingIndex >= _sampleRate - 1) {
                    _dataLine.write(_buffer, 0, _sampleRate);
                    _processingIndex = 0;
                }
            }
        } else if (_dataLine != null) {
            _dataLine.drain();
        }
    }

    public void play(int[] indexes) {
        _events = new SoundEvent[indexes.length];
        for(int i = 0; i < indexes.length; i++){
            float frequency = noteToFrequency(indexes[i] - 12);
            _events[i] = new SoundEvent(frequency / _sampleRate);
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
