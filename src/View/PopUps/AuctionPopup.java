package View.PopUps;

import Controller.Controller;
import Model.AbstractPlayer;
import Model.properties.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

public class AuctionPopup implements PropertyChangeListener {
    private VBox myLayout;
    private ResourceBundle messages;
    private String name;
    private List<AbstractPlayer> myPlayers;
    private AbstractPlayer myCurrPlayer;
    private Stage oldPopUp;
    private BidBoxGroup myBidBoxGroup;
    private SimpleBooleanProperty auctionOver;
    private Controller myController;
    private Property myProperty;
    private int myPropLocation;

    public AuctionPopup(int propLocation, String name, Controller controller, Stage popUpWindow, List<AbstractPlayer> players, AbstractPlayer currPlayer) {
        this.myController = controller;
        this.myPropLocation = propLocation;
        this.messages = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(2).toString());
        this.name = name;
        this.myPlayers = players;
        this.oldPopUp = popUpWindow;
        this.myCurrPlayer = currPlayer;

        this.myLayout = new VBox(20);

        setUpLayout();

        this.auctionOver = new SimpleBooleanProperty(false);
        myBidBoxGroup.startAuction();
        auctionOver.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1 == true){
                    endAuction();
                }
            }
        });
    }

    private void setUpLayout(){
        oldPopUp.setTitle(createTitle());

        Label header = createHeader();
        VBox boxes = createBidBox();

        myLayout.getChildren().addAll(header,boxes);
    }

    private VBox createBidBox(){
        VBox buttonBox = new VBox();
        buttonBox.setSpacing(3);

        myBidBoxGroup = new BidBoxGroup();
        myBidBoxGroup.addPropertyChangeListener("endAuction",this);

        for (AbstractPlayer p: myPlayers){
            BidBox pBid = new BidBox(p,myBidBoxGroup);
            myBidBoxGroup.addBox(pBid);
            buttonBox.getChildren().addAll(pBid.getView());
        }

        return buttonBox;
    }

    private void endAuction(){
        System.out.println("end auction");
        if (myBidBoxGroup.getHighestBidder() == null){
            // No one wanted to buy the property. Do nothing, return to game
            oldPopUp.close();
        }
        else {
            AbstractPlayer winner = myBidBoxGroup.getHighestBidder().getMyPlayer();
            int bid = myBidBoxGroup.getHighestBid();
            myProperty = myController.getGame().handleAuction(winner, bid, myPropLocation);

            displayWinnerOfAuction(myBidBoxGroup.getHighestBid(),myBidBoxGroup.getHighestBidder().getMyPlayer());
        }
    }

    private void displayWinnerOfAuction(int winningBid, AbstractPlayer winningBidder){
        myLayout = new VBox(20);
        Label msg = new Label(winningBidder.getName() + " won the Auction");
        Label msg2 = new Label("bought " + myProperty.getName() + " for "+winningBid);
        Button ok = new Button("OK");
        ok.setOnAction(e -> oldPopUp.close());
        myLayout.getChildren().addAll(msg,msg2,ok);
        display();
    }

    private String createTitle() {
        return messages.getString("auctionTitle");
    }

    private Label createHeader() {
        return new Label(messages.getString("auctionHeader") + name + "!");
    }

    public void display(){
        Scene scene = new Scene(myLayout, Controller.WIDTH/2, Controller.HEIGHT/1.5);
        scene.getStylesheets().add(( new File("data/GUI.css") ).toURI().toString());
        oldPopUp.setScene(scene);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        endAuction();
    }
}
