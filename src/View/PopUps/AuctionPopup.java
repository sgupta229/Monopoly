package View.PopUps;

import Controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class AuctionPopup extends BuyPropertyPopup {

    private String name;
    private int prefWidthTextBox=125;
    private ResourceBundle myText;

    public AuctionPopup(int propLocation, String name, Controller controller, Stage popUpWindow) {
        super(propLocation, controller);
        this.name = name;
        this.myText = super.getMessages();
        popUpWindow.close();
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        return super.createImage(scene, popUpWindow);
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(HBoxSpacing);
        Button button2= new Button(myText.getString("okButton"));
        button2.setId("button2");
        button2.setOnAction(e -> window.close());

        TextField searchInput = new TextField();
        searchInput.setPromptText(myText.getString("auctionText"));
        searchInput.setPrefWidth(prefWidthTextBox);

        buttons.getChildren().addAll(searchInput,button2);

        return buttons;
    }

    @Override
    protected String createMessage() {
        return myText.getString("auctionMessage");
    }

    @Override
    protected String createTitle() {
        return myText.getString("auctionTitle");
    }

    @Override
    protected Label createHeader() {
        return new Label(myText.getString("auctionHeader") + name + "!");
    }
}
