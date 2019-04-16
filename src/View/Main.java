package View;

import Controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage s) {
        Controller controller = new Controller(s);
        controller.goToChooseGameScreen();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
