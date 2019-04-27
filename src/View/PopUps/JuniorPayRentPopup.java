package View.PopUps;

import Controller.Controller;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class JuniorPayRentPopup extends JuniorBuyPropertyPopup {

    private AbstractSpace mySpace;
    private Controller myController;
    private Property myProp;
    private ResourceBundle myText;

    public JuniorPayRentPopup(int propLocation, Controller controller) {
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
        Button button1 = new Button(myText.getString("payButton"));
        super.createButtonHandler(button1,window);
        buttons.getChildren().addAll(button1);
        return buttons;
    }

    @Override
    protected String createMessage() {
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



