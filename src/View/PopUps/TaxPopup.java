package View.PopUps;

import Controller.Controller;
import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.ResourceBundle;

public class TaxPopup extends Popup {
    private List<AbstractSpace> spaces;
    private String name;
    private int propLocation;
    private Controller myController;
    private AbstractSpace mySpace;
    private ResourceBundle myText;

    public TaxPopup(int propLocation, Controller controller) {
        super();
        this.propLocation = propLocation;
        this.myController = controller;
        this.myText = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(POPUP_TEXT).toString());
        BoardConfigReader spaceInfo = new BoardConfigReader(myController.getGame());
        spaces = spaceInfo.getSpaces();
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
        var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream("taxCard.png"));
        ImageView image = new ImageView(imageFile);

        return new Pane(image);
    }

    @Override
    protected String createMessage() {
        return myText.getString("taxMessage");
    }

    @Override
    protected String createTitle() {
        return myText.getString("taxTitle");
    }

    @Override
    protected Pane createButtons(Stage window) {
        VBox buttons = new VBox(HBOX_SPACING_TEN);
        Button button1= new Button(myText.getString("taxButton1"));
        Button button2= new Button(myText.getString("taxButton2"));

        button1.setId("button3");
        button2.setId("button3");

        button1.setOnAction(e -> buttonHandler(OK,window));
        button2.setOnAction(e -> buttonHandler(NO,window));
        buttons.getChildren().addAll(button1,button2);
        buttons.setPadding(new Insets(OK,OK,HBOX_SPACING_FORTY,OK));
        return buttons;
    }

    private void buttonHandler(int num, Stage window){
        mySpace.doAction(myController.getGame(),num);
        window.close();
    }

    @Override
    protected Label createHeader() {
        return new Label(name);
    }
}
