package panel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

final public class Keyboard extends Canvas {
    private GraphicsContext _gc;
    private short _height = 180;

    public Keyboard() {
        super();
        setHeight(185);
        setWidth(720);
        _gc = getGraphicsContext2D();
        for (short i = 0; i <= 24; i++) {
            renderNote(i , false);
        }

    }

    public void renderNote(short i, boolean isActive) {
        _gc.setFill(Color.GRAY);
        _gc.fillRect(i * 30, 0, 30 , 185);
        short h = (short) (isActive ? (_height - 20) : _height);
        short keyId = (short)( (i + 1) % 12);
        switch (keyId) {
            case 2:
            case 4:
            case 7:
            case 9:
            case 11:
                _gc.setFill(Color.BLACK);
                _gc.fillRect(i * 30, 0, 30, h);
                break;
            default:
                _gc.setFill(Color.WHITE);
                _gc.fillRect(i * 30 + 1, 0, 30 - 1, h);
                break;
        }
    }
}
