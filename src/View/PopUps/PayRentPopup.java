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

public class PayRentPopup extends BuyPropertyPopup {

    private AbstractSpace mySpace;
    private Controller myController;
    private Property myProp;


    public PayRentPopup(int propLocation, Controller controller) {
        super(propLocation, controller);
        this.myController = controller;
        BoardConfigReader spaceInfo = new BoardConfigReader();
        allSpaces = spaceInfo.getSpaces();
        allProps = spaceInfo.getProperties();
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
        HBox buttons = new HBox(HBoxSpacing);
        Button button2= new Button("PAY");
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
        String myMessage = "Oops this property is owned by " + myController.getGame().getBank().propertyOwnedBy(myProp).getName() + ". Rent is $" + myProp.calculateRent(myController.getGame().getBank().propertyOwnedBy(myProp), myController.getGame().getLastDiceRoll()) + "0.";
        return myMessage;
    }

    @Override
    protected String createTitle() {
        String myTitle = "Rent";
        return myTitle;
    }


    @Override
    protected Label createHeader() {
        Label title = new Label("Pay rent!");
        return title;
    }
}

