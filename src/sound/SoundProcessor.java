package sound;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class SoundProcessor
{

	private String threadName;
	private final AudioFormat _audioFormat;
	private SourceDataLine _dataLine;
	// audio Quality
	protected int _quality = 16;
	// count of Samples(Elements) per second in Buffer(Array)
	protected int _sampleRate =    8 * 1024;
	private double _waveIndex = 0;
	// frequency of signal
	private float _frequancy = 100;
	// difference of sinus index per sample
	private double _stepSize;
	public boolean isPlaying = false;
	private byte[] _buffer;
	
	public SoundProcessor()
	{
		threadName = "processor";
		_audioFormat = new AudioFormat(_sampleRate, _quality , 1, true, false);
		_buffer = new byte[_sampleRate];
		try {
			_dataLine = AudioSystem.getSourceDataLine(_audioFormat);
			_dataLine.open(_audioFormat, _sampleRate);
			_dataLine.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	float noteToFrequency(int index) {
		float power = ((float) index - 49) / 12;
		return (float) Math.pow(2, power) * 440;
	}

	byte singleWave() {
		if (!isPlaying) return (byte) 0;
		_waveIndex = (_waveIndex + _stepSize) % 1;
		return (byte) (Wave.mixed(_waveIndex) * 125f);
	}

	public void next() {
	    if(isPlaying) {
            for (int i = 0; i < _sampleRate; i++) {
                _buffer[i] = singleWave();
            }
            _dataLine.write(_buffer, 0, _sampleRate);
        }else{
	        _dataLine.drain();
        }
	}

	public void play(int index) {
		_frequancy = noteToFrequency(index - 12);
		_stepSize = _frequancy / _sampleRate;
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
