package panel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//local imports
import sound.SoundProcessor;

public class SynthFrame extends JFrame
{
    private Keyboard _keyboard;

    public SynthFrame()
    {
        super("Synth");
        setSize(720, 203);
        setResizable(false);
        setLocation(50, 50);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT, 2 ,0));
        _keyboard = new Keyboard();
        add(_keyboard);
    }

    public void setSoundProcessor(final SoundProcessor sound) {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                Point point = evt.getPoint();
                int x = (int) point.x / 30 + 1;
                _keyboard.updateState(x);
                sound.play(x);
            }
            public void mouseClicked(MouseEvent evt) {
                _keyboard.updateState(-1);
                sound.stop();
            }
        });
    }
}
