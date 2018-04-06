import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import panel.Keyboard;
import panel.Note;
import sound.SoundProcessor;

class SoundManager {
    private SoundProcessor[] activeSoundProcessors = new SoundProcessor[49];

    public void add(int note) {

        if(note < 0 || note > 49) return;

        if (activeSoundProcessors[note] != null) {
            activeSoundProcessors[note].stop();
        }
        activeSoundProcessors[note] = new SoundProcessor(note);
    }

    public void remove (int note){

        if(note < 0 || note > 49) return;

        if (activeSoundProcessors[note] != null) {
            activeSoundProcessors[note].stop();
            activeSoundProcessors[note] = null;
        }
    }
}


public class Main extends Application {
    private boolean _isLive = true;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Synthesizer");
        StackPane layout = new StackPane();
        final Keyboard can = new Keyboard();
        can.paint();
        layout.getChildren().add(can);

        Scene scene = new Scene(layout, 720, 190);
        primaryStage.setScene(scene);
        primaryStage.show();

        Note notes = new Note();
        SoundManager sounds = new SoundManager();

        can.setOnMousePressed((MouseEvent event) -> {
            int note = (int) (event.getSceneX() / 30) + 1;
            if (!notes.notes.contains(note)) {
                notes.onClick(event);
                can.updateState(notes.notes);
                sounds.add(note);
            }
        });

        scene.setOnKeyPressed((KeyEvent event) -> {
            int note = notes.noteFromKeyEvent(event);
            if (!notes.notes.contains(note)) {
                notes.keyPress(event);
                can.updateState(notes.notes);
                sounds.add(note);
            }
        });

        can.setOnMouseReleased((MouseEvent event) -> {
            int note = (int) (event.getSceneX() / 30) + 1;
            notes.onRelease(event);
            can.updateState(notes.notes);
            sounds.remove(note);
        });

        scene.setOnKeyReleased((KeyEvent event) -> {
            int note = notes.noteFromKeyEvent(event);
            notes.keyRelease(event);
            can.updateState(notes.notes);
            sounds.remove(note);
        });

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            _isLive = false;
        });
    }
}

