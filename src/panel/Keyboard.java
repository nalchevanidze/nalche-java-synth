package panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class Keyboard extends JPanel
{
	private int _height;
	private int _active;

	Keyboard()
	{
		super();
        _height = 180;

	}

	public void updateState(int note) {
		_active = note;
		repaint();
	}

	public void paint(Graphics g) {
		
		setSize(720, 203);
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, 720, 203);
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, 0, 720, 203);
		
		for(int i = 0; i <= 24; i++) {
			
			int h = _active==(i+1)? (_height - 20) : _height;
			
			int keyId = (i+1)%12;
			
			switch (keyId)
			{
				case 2:
				case 4:
				case 7:
				case 9:
				case 11:
					g2d.setColor(Color.BLACK);
					g2d.fillRect(i*30, 0, 30, h);
					break;
				default:
					g2d.setColor(Color.WHITE);
					g2d.fillRect(i*30, 0, 30, h);
					g2d.setColor(Color.GRAY.brighter());
					g2d.drawRect(i*30, 0, 30, h);
					break;
			}
			
		}
		
	}
}
