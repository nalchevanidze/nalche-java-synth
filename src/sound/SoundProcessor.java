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
    private int _processingIndex = 0;

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
        if (!isPlaying) return (byte) 0;
        _waveIndex = (_waveIndex + _stepSize) % 1;
        return (byte) (Wave.saw(_waveIndex) * 125f);
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

    public void play(int index) {

        float frequency = noteToFrequency(index - 12);
        _stepSize = frequency / _sampleRate;
        isPlaying = true;
    }

    public void stop() {
        isPlaying = false;
    }

    public void terminate() {
        _dataLine.drain();
        _dataLine.close();

    }

}
