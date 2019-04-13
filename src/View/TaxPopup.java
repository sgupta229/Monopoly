package View;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TaxPopup extends Popup {
    public TaxPopup(String title, String message) {
        super(title, message);
    }

    @Override
    protected Pane createImage(Scene scene) {
        return null;
    }

    @Override
    protected Pane createButtons(Stage window) {
        return null;
    }

//    @Override
//    protected Scene setSizeOfPopup(BorderPane layout) {
//        return null;
//    }

    @Override
    protected Label createHeader() {
        return null;
    }
}
