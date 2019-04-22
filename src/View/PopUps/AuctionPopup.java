package View.PopUps;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AuctionPopup extends BuyPropertyPopup {

    private String name;
    private int prefWidthTextBox=125;
    private ResourceBundle myText;
    private List<AbstractPlayer> myPlayers;
    private AbstractPlayer myCurrPlayer;
    private Stage oldPopUp;

    public AuctionPopup(int propLocation, String name, Controller controller, Stage popUpWindow, List<AbstractPlayer> players, AbstractPlayer currPlayer) {
        super(propLocation, controller);
        this.name = name;
        this.myText = super.getMessages();
        this.myPlayers = players;
        this.oldPopUp = popUpWindow;
        this.myCurrPlayer = currPlayer;
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
        button2.setOnAction(e -> {
            window.close();
            oldPopUp.close();
        });

        VBox bids = new VBox(HBoxSpacing);
        for (AbstractPlayer p: myPlayers){
            if (!p.equals(myCurrPlayer)){
                TextField searchInput = new TextField();
                Label player = new Label(p.getName());
                searchInput.setPrefWidth(prefWidthTextBox);
                bids.getChildren().addAll(player,searchInput);
            }
        }
        buttons.getChildren().addAll(bids,button2);
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
