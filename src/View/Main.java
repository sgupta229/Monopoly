package View;

import javafx.application.Application;
import javafx.stage.Stage;
import java.awt.Toolkit;

public class Main extends Application {
    public static final String TITLE = "Monopoly";
    public static final double HEIGHT = 700;
    public static final double WIDTH = 1200;
//    public static final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//    public static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();



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
