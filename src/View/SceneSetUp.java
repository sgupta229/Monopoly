package View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;


public class SceneSetUp {
    private static final double FRAMES_PER_SECOND = 1;
    private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private Group myRoot = new Group();
    private Timeline animation = new Timeline();
    private Scene myScene;
    private ChooseGameScreen myWelcomeScreen;

    public SceneSetUp(double width, double height, Stage stage) {
        setupAnimation();
        //SHOULD BE ABLE TO CHANGE COLOR BASED ON WHICH GAME IS LOADED IN
        Paint background = Color.WHITE;
        myScene = new Scene(myRoot, width, height, background);
        myScene.getStylesheets().add(fileToStylesheetString( new File("resources/GUI.css") ));
        myWelcomeScreen = new ChooseGameScreen(myRoot, width, height);
    }

    private void setupAnimation(){
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> this.step());
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void step() {
    }

    private String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }

    public Scene getScene() { return myScene; }
}
