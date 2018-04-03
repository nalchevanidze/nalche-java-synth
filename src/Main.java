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

        can.setOnMousePressed((MouseEvent event) -> {
            int note = (int) (event.getSceneX() / 30) + 1;
            if(!notes.notes.contains(note)) {
                notes.onClick(event);
                can.updateState(notes.notes);
                new SoundProcessor(note);
            }
        });

        scene.setOnKeyPressed((KeyEvent event) -> {
            int note = notes.noteFromKeyEvent(event);
            if(!notes.notes.contains(note)) {
                notes.keyPress(event);
                can.updateState(notes.notes);
                new SoundProcessor(note);
            }
        });

        can.setOnMouseReleased((MouseEvent event) -> {
            notes.onRelease(event);
            can.updateState(notes.notes);
        });

        scene.setOnKeyReleased((KeyEvent event) -> {
            notes.keyRelease(event);
            can.updateState(notes.notes);
        });

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            _isLive = false;
        });
    }
}

