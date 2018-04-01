package panel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


public class Keyboard extends Canvas
{
	private int _height;
	private int _active;
	GraphicsContext _gc;


	public Keyboard()
	{
		super();
		_height = 180;
		setHeight(185);
		setWidth(720);
		 _gc = getGraphicsContext2D();

	}
	public void updateState(int note) {
		_active = note;
		paint();
	}

	public void paint() {
		_gc.setFill(Color.GRAY);
		_gc.fillRect(0, 0, 720, 185);
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
					_gc.setFill(Color.BLACK);
					_gc.fillRect(i*30, 0, 30, h);
					break;
				default:
					_gc.setFill(Color.WHITE);
					_gc.fillRect(i*30 +1, 0, 30 -1, h);
					break;
			}
		}
	}
}
