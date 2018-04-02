import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import panel.Keyboard;
import sound.SoundProcessor;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Synthesizer");
        StackPane layout = new StackPane();
        final Keyboard can = new Keyboard();
        can.paint();
        layout.getChildren().add(can);

        Scene scene = new Scene(layout, 200, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        final SoundProcessor sound = new SoundProcessor();

        can.setOnMousePressed((MouseEvent event) -> {
            can.updateState(10);
            sound.play(10);
        });

        can.setOnMouseReleased((MouseEvent event) -> {
            can.updateState(-1);
            sound.stop();
        });

        SoundRunner soundRunner = new SoundRunner(sound);
        soundRunner.start();

    }
}

