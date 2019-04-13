package View.PopUps;

import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import View.PopUps.Popup;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class CornerPopup extends Popup {

    private int propLocation;
    private Text message;
    private String name;
    private List<AbstractSpace> spaces;


    public CornerPopup(String title, int propLocation) {
        super(title);
        this.propLocation = propLocation;
        BoardConfigReader spaceInfo = new BoardConfigReader();
        spaces = spaceInfo.getSpaces();
    }

    @Override
    protected Pane createImage(Scene scene) {
        for (AbstractSpace sp : spaces) {
            if (sp.getMyLocation() == propLocation) {
                name = sp.getMyName();
            }
        }
        message = new Text("You landed on " + name);
        StackPane messagePane = new StackPane();
        messagePane.getChildren().add(message);
        return messagePane;
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(10);
        Button button1= new Button("OK");
        button1.setId("button1");
        button1.setOnAction(e -> window.close());
        buttons.getChildren().add(button1);
        return buttons;
    }

    @Override
    protected Label createHeader() { return new Label(name);}
}
