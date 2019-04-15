package View.PopUps;

import Controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PayRentPopup extends BuyPropertyPopup {

    public PayRentPopup(int propLocation, Controller controller) {
        super(propLocation, controller);

    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        return super.createImage(scene, popUpWindow);
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(10);
        Button button2= new Button("PAY");
        button2.setId("button2");
        button2.setOnAction(e -> window.close());

        buttons.getChildren().addAll(button2);

        return buttons;
    }

    @Override
    protected String createMessage() {
        String myMessage = "Oops this property is owned by ____. Rent is ???.";
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

