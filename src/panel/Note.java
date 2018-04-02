package panel;
import javafx.scene.input.KeyEvent;

public class Note {
    public int get(KeyEvent event) {
        char key = event.getCode().getName().toLowerCase().charAt(0);

        switch (key) {
            case 'y':
                return 1;
            case 's':
                return 2;
            case 'x':
                return 3;
            case 'd':
                return 4;
            case 'c':
                return 5;
            case 'v':
                return 6;
            case 'g':
                return 7;
            case 'b':
                return 8;
            case 'h':
                return 9;
            case 'n':
                return 10;
            case 'j':
                return 11;
            case 'm':
                return 12;
            case 'q':
                return 13;
            case '2':
                return 14;
            case 'w':
                return 15;
            case '3':
                return 16;
            case 'e':
                return 17;
            case 'r':
                return 18;
            case '5':
                return 19;
            case 't':
                return 20;
            case '6':
                return 21;
            case 'z':
                return 22;
            case '7':
                return 23;
            case 'u':
                return 24;
        }
        return  -1;
    }
}