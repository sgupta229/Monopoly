package View;

import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application {
    public static final String TITLE = "Monopoly";
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final double HEIGHT = SCREEN_SIZE.getHeight();
    public static final double WIDTH = SCREEN_SIZE.getWidth();

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage s) {
        SceneSetUp myScene = new SceneSetUp(WIDTH,HEIGHT,s);
        s.setScene(myScene.getScene());
        s.setTitle(TITLE);
        s.show();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
