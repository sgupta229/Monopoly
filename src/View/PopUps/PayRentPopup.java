package View.PopUps;

import Controller.Controller;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class PayRentPopup extends BuyPropertyPopup {

    private AbstractSpace mySpace;
    private Controller myController;
    private Property myProp;
    private ResourceBundle myText;

    public PayRentPopup(int propLocation, Controller controller) {
        super(propLocation, controller);
        this.myController = controller;
        BoardConfigReader spaceInfo = new BoardConfigReader(myController.getGame());
        allSpaces = spaceInfo.getSpaces();
        allProps = spaceInfo.getProperties();
        this.myText = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(POPUP_TEXT).toString());
        for (AbstractSpace sp : allSpaces) {
            if (sp.getMyLocation() == propLocation) {
                mySpace = sp;
            }
        }
        for (Property p : allProps) {
            if (p.getName().equals(mySpace.getMyName())) {
                myProp = p;
            }
        }
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        return super.createImage(scene, popUpWindow);
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(HBOX_SPACING_TEN);
        Button button2= new Button(myText.getString("payButton"));
        button2.setId("button2");
        button2.setOnAction(e -> window.close());

        button2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                mySpace.doAction(myController.getGame(),OK);
                window.close();
            }
        });
        buttons.getChildren().addAll(button2);

        return buttons;
    }

    @Override
    protected String createMessage() {
        if(myController.getGame().getBank().propertyOwnedBy(myProp)==null){
            System.out.println("noooo null");
        }
        else{
            System.out.println("the owner isn't null...");
        }
        System.out.println("1 " +myController.getGame().getBank().propertyOwnedBy(myProp));
        System.out.println("2 "+ myProp.calculateRent(myController.getGame().getBank().propertyOwnedBy(myProp), myController.getGame().getLastDiceRoll()));
        return myText.getString("propOwned") + myController.getGame().getBank().propertyOwnedBy(myProp).getName() + myText.getString("rentIs") + myProp.calculateRent(myController.getGame().getBank().propertyOwnedBy(myProp), myController.getGame().getLastDiceRoll()) + "0.";
    }

    @Override
    protected String createTitle() {
        return myText.getString("rentTitle");
    }


    @Override
    protected Label createHeader() {
        return new Label(myText.getString("rentHeader"));
    }
}

