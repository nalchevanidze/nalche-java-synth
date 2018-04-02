package sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class SoundProcessor {

    private SourceDataLine _dataLine;
    private int _sampleRate = 8 * 1024;
    private double _waveIndex = 0;
    private double _stepSize;
    private boolean isPlaying = false;
    private byte[] _buffer;

    public SoundProcessor() {
        AudioFormat audioFormat = new AudioFormat(_sampleRate, 16, 1, true, false);
        _buffer = new byte[_sampleRate];
        try {
            _dataLine = AudioSystem.getSourceDataLine(audioFormat);
            _dataLine.open(audioFormat, _sampleRate);
            _dataLine.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private float noteToFrequency(int index) {
        float power = ((float) index - 49) / 12;
        return (float) Math.pow(2, power) * 440;
    }

    private byte singleWave() {
        if (!isPlaying) return (byte) 0;
        _waveIndex = (_waveIndex + _stepSize) % 1;
        return (byte) (Wave.mixed(_waveIndex) * 125f);
    }

    public void next() {
        if (isPlaying) {
            for (int i = 0; i < _sampleRate; i++) {
                _buffer[i] = singleWave();
            }
            _dataLine.write(_buffer, 0, _sampleRate);
        } else {
            _dataLine.drain();
        }
    }

    public void play(int index) {
        float frequency = noteToFrequency(index - 12);
        _stepSize = frequency / _sampleRate;
        isPlaying = true;
    }

    public void stop() {
        isPlaying = false;
        _dataLine.drain();
    }

    public void terminate() {
        _dataLine.close();
    }

}
