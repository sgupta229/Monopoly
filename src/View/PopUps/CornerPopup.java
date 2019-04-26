package View.PopUps;

import Controller.Controller;
import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.ResourceBundle;

public class CornerPopup extends Popup {

    private int propLocation;
    private Text message;
    private String name;
    private List<AbstractSpace> spaces;
    private ResourceBundle myText;
    private AbstractSpace mySpace;
    private Controller myController;

    public CornerPopup(int propLocation, Controller controller) {
        super();
        this.propLocation = propLocation;
        this.myController = controller;
        BoardConfigReader spaceInfo = new BoardConfigReader(myController.getGame());
        spaces = spaceInfo.getSpaces();
        this.myText = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(POPUP_TEXT).toString());
        for (AbstractSpace sp : spaces) {
            if (sp.getMyLocation() == propLocation) {
                mySpace = sp;
            }
        }
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        for (AbstractSpace sp : spaces) {
            if (sp.getMyLocation() == propLocation) {
                name = sp.getMyName();
            }
        }
        message = new Text(myText.getString("cornerMessage") + name);
        StackPane messagePane = new StackPane();
        messagePane.getChildren().add(message);
        return messagePane;
    }

    @Override
    protected String createMessage() {
        return "";
    }

    @Override
    protected String createTitle() {
        return myText.getString("cornerTitle");
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(HBOX_SPACING_TEN);
        Button button1= new Button("OK");
        button1.setId("button1");
        button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                mySpace.doAction(myController.getGame(), OK);
                System.out.println("called do action");
                window.close();
            }
        });
        buttons.getChildren().add(button1);
        return buttons;
    }

    @Override
    protected Label createHeader() { return new Label(name);}
}
