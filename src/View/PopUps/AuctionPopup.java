package View.PopUps;

import Controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AuctionPopup extends BuyPropertyPopup {

    private String name;


    public AuctionPopup(int propLocation, String name, Controller controller, Stage popUpWindow) {
        super(propLocation, controller);
        this.name = name;
        popUpWindow.close();
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        return super.createImage(scene, popUpWindow);
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(10);
        Button button2= new Button("OK");
        button2.setId("button2");
//        button2.setOnAction(e -> new AuctionPopup("Auction", "Player #, Enter your bid amount.", propLocation));
        button2.setOnAction(e -> window.close());

        TextField searchInput = new TextField();
        searchInput.setPromptText("Enter Amount");
        searchInput.setPrefWidth(125);

        buttons.getChildren().addAll(searchInput,button2);

        return buttons;
    }


    @Override
    protected String createMessage() {
        String myMessage = "Player #, would you like to purchase this property?";
        return myMessage;
    }

    @Override
    protected String createTitle() {
        String myTitle =  "Auction";
        return myTitle;
    }

    @Override
    protected Label createHeader() {
        Label title = new Label("Auction for " + name + "!");
        return title;
    }
}
