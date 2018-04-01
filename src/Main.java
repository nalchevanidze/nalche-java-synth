import panel.SynthFrame;
import sound.SoundProcessor;

public class Main {


    public static void main(String[] args){

        SoundProcessor sound = new SoundProcessor();
        SynthFrame panel = new SynthFrame();
        panel.setSoundProcessor(sound);
        SoundRunner soundRunner = new SoundRunner(sound);
        soundRunner.start();

    }


}

