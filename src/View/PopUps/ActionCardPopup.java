package View.PopUps;

import Controller.Controller;
import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.List;

public class ActionCardPopup extends Popup {

    private List<AbstractSpace> spaces;
    private int propLocation;
    private String name;
    private String title;
    private AbstractSpace mySpace;
    private Controller myController;
    private String myMessage;

    public ActionCardPopup(int propLocation, Controller controller){
        super();
        this.propLocation = propLocation;
        this.myController = controller;
        BoardConfigReader indexToName = new BoardConfigReader();
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
                if (name.equalsIgnoreCase("CHANCE")) {
                    myImage = "chance.png";
                } else if (name.equalsIgnoreCase("COMMUNITY_CHEST")) {
                    name.replace("_"," ");
                    myImage = "chest.png";
                }
            }
            title = name;
        }
        var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myImage));
        ImageView image = new ImageView(imageFile);
        Pane imagePane= new Pane(image);
        return imagePane;
    }

    @Override
    protected String createMessage() {
        return myMessage;
    }

    @Override
    protected String createTitle() {
        String myTitle = "Pay Rent";
        return myTitle;
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(HBoxSpacing);
        Button button1= new Button("OK");
        button1.setId("button1");

        button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                myController.getGame().getCurrentActionCard().doCardAction(myController.getGame());
                window.close();
            }
        });
        buttons.getChildren().add(button1);
        return buttons;
    }

    @Override
    protected Label createHeader() {
        return new Label(title);
    }
}
