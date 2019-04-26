package View.PopUps;

import Model.AbstractPlayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.NumberSpinner;

public class BidBox {
    private BidBoxGroup myGroup;
    private AbstractPlayer myPlayer;

    private Label playerName;
    private NumberSpinner spinner;
    private Button bidButton;
    private Button foldButton;

    private Pane view;

    public BidBox(AbstractPlayer player, BidBoxGroup group){
        this.myPlayer = player;
        this.myGroup = group;

        view = new Pane();
        VBox v = new VBox();

        playerName = new Label(myPlayer.getName());
        spinner = new NumberSpinner(Popup.BIDDING_STEP,Popup.BIDDING_STEP);
        spinner.setLowerBound(Popup.BIDDING_STEP);
        HBox buttonBox = createButtonsHBox();

        v.getChildren().addAll(playerName,spinner,buttonBox);
        view.getChildren().addAll(v);
    }

    private HBox createButtonsHBox(){
        HBox buttonBox = new HBox();

        bidButton = new Button("BID");
        bidButton.setOnAction(e -> myGroup.respondToBid(getBidValue(),this));
        foldButton = new Button("FOLD");
        foldButton.setOnAction(e -> myGroup.respondToFold(this));

        buttonBox.getChildren().addAll(bidButton,foldButton);
        return buttonBox;
    }

    private int getBidValue(){
        return spinner.getNumber();
    }

    public void disable(boolean b){
        spinner.setDisable(b);
        bidButton.setDisable(b);
        foldButton.setDisable(b);
    }

    public void remove(){
        view.setStyle("-fx-background-color: #cfcfcf");
        System.out.println("style set");
    }

    public void setLowerBound(int value){
        spinner.setLowerBound(value);
    }

    public void setInitalBidValue(int value){
        spinner.setNumber(value);
    }

    public AbstractPlayer getMyPlayer(){
        return myPlayer;
    }

    public Pane getView(){
        return view;
    }
}
