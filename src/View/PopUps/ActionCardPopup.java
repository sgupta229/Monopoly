package View.PopUps;

import Controller.Controller;
import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.List;
import java.util.ResourceBundle;

public class ActionCardPopup extends Popup {

    private List<AbstractSpace> spaces;
    private int propLocation;
    private String name;
    private String title;
    private AbstractSpace mySpace;
    private Controller myController;
    private String myMessage;
    private ResourceBundle myText;

    public ActionCardPopup(int propLocation, Controller controller){
        super();
        this.propLocation = propLocation;
        this.myController = controller;
        this.myText = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(POPUP_TEXT).toString());
        BoardConfigReader indexToName = new BoardConfigReader(myController.getGame());
        spaces = indexToName.getSpaces();
        for (AbstractSpace sp : spaces){
            if (sp.getMyLocation()==propLocation){
                mySpace = sp;
            }
        }
        mySpace.doAction(myController.getGame(),OK);
        myMessage = myController.getGame().getCurrentActionCard().getMyMessage();
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        String myImage = "";
        for (AbstractSpace sp : spaces) {
            if (sp.getMyLocation() == propLocation) {
                name = sp.getMyName();
                if (name.equalsIgnoreCase(myText.getString("actionSpace1"))) {
                    myImage = myText.getString("actionSpace1Image");
                } else  {
                    myImage = myText.getString("actionSpace2Image");
                }
            }
            title = name;
        }
        var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myImage));
        ImageView image = new ImageView(imageFile);
        return new Pane(image);
    }

    @Override
    protected String createMessage() {
        return myMessage;
    }

    @Override
    protected String createTitle() {
        return myText.getString("actionCard");
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(HBOX_SPACING_TEN);
        Button button1= new Button(myText.getString("okButton"));
        button1.setId("button1");

        button1.setOnAction(e ->{
                myController.getGame().getCurrentActionCard().doCardAction(myController.getGame());
                window.close();
        });
        buttons.getChildren().add(button1);
        return buttons;
    }

    @Override
    protected Label createHeader() {
        return new Label(title);
    }
}
