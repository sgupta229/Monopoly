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

import java.util.List;
import java.util.ResourceBundle;

public class TradePopup extends BuyPropertyPopup {

    private String name;
    private int prefWidthTextBox=125;
    private ResourceBundle myText;
    private List<AbstractPlayer> myPlayers;
    private AbstractPlayer myCurrPlayer;
    private Stage oldPopUp;



    public TradePopup(int propLocation, String name, Controller controller, Stage popUpWindow, List<AbstractPlayer> players, AbstractPlayer currPlayer) {
        super(propLocation, controller);
        this.name = name;
        this.myText = ResourceBundle.getBundle(controller.getGame().getFrontEndFiles().get(2).toString());
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
        Button button1 = new Button("");
        Button button2 = new Button("NO");
        button1.setId("button2");
        button2.setId("button2");
        return buttons;
    }

    @Override
    protected String createMessage() {
        return myText.getString("tradeMessage");
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
