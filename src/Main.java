import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import panel.Keyboard;
import sound.SoundProcessor;
import panel.Note;


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

        final SoundProcessor sound = new SoundProcessor();

        Note notes = new Note();

        can.setOnMousePressed((MouseEvent event) -> {
            int note = (int)(event.getSceneX()/30) + 1;
            can.updateState(note);
            sound.play(note);
        });

        can.setOnMouseReleased((MouseEvent event) -> {
            can.updateState(-1);
            sound.stop();
        });

        primaryStage.setOnCloseRequest((WindowEvent event)-> {
            _isLive = false;
        });

        scene.setOnKeyPressed( (KeyEvent event)-> {
            int note = notes.get(event);
            can.updateState(note);
            sound.play(note);
        });

        scene.setOnKeyReleased( (KeyEvent event)-> {
            can.updateState(-1);
            sound.stop();
        });
        
        Thread soundRunner = new Thread(()->{
                try {
                    while (_isLive) {
                        sound.next();
                        Thread.sleep(120);
                    }
                    sound.terminate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        soundRunner.start();
    }
}

