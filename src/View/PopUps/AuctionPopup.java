package View.PopUps;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AuctionPopup extends BuyPropertyPopup {

    private String name;


    public AuctionPopup(String title, String message, int propLocation, String name) {
        super(title, message, propLocation);
        this.name = name;
    }

    @Override
    protected Pane createImage(Scene scene) {
        return super.createImage(scene);
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
    protected Label createHeader() {
        Label title = new Label("Auction for " + name + "!");
        return title;
    }
}
