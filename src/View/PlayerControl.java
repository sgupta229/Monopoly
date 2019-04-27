package View;

import Controller.Controller;
import Model.AbstractPlayer;
import Model.actioncards.AbstractActionCard;
import Model.properties.Property;
import View.PopUps.BuildOrSellPopup;
import View.PopUps.Popup;
import View.PopUps.TradePopup;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class PlayerControl implements PropertyChangeListener {
    private Controller myController;
    protected AbstractPlayer myPlayer;
    private ResourceBundle messages;
    private AnchorPane myAnchorPane;
    private VBox myVBox;
    private DiceRoller myDiceRoller;
    private Text myFunds;
    private Button endTurnButton;
    private Button bailButton;

    public PlayerControl(AbstractPlayer player, Controller controller){
        myPlayer = player;
        myController = controller;
        myPlayer.addPropertyChangeListener("funds",this);

        messages = ResourceBundle.getBundle("Messages");

        setUpLayout();
    }

    private void setUpLayout(){
        myAnchorPane = new AnchorPane();

        VBox myVBox = createVBox();
        myDiceRoller = new DiceRoller(myController, endTurnButton, bailButton);
        HBox diceRollerView = myDiceRoller.getDiceRollerView();

        myAnchorPane.getChildren().addAll(myVBox,diceRollerView);
        AnchorPane.setTopAnchor(myVBox,20.0);
        AnchorPane.setBottomAnchor(diceRollerView,20.0);
    }


    private VBox createVBox(){

        myVBox = new VBox();
        myVBox.setId("playerControlBox");

        endTurnButton = new Button(messages.getString("end-turn"));
        endTurnButton.setDisable(true);
        endTurnButton.setOnAction(e -> {
            myController.getGame().startNextTurn();
            myDiceRoller.setDisable(false);
            if (myController.getGame().checkGameOver()) {
                myController.endGame(myController.getGame().getWinner());
            }
            if(myController.getGame().getRightPlayer().getCantPayBool()) {
                Alert removePlayer = new Alert(Alert.AlertType.WARNING);
                removePlayer.setContentText("You went bankrupt :(");
                removePlayer.show();
                Optional<ButtonType> result = removePlayer.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    myController.getGame().getPlayers().remove(myController.getGame().getCurrPlayer());
                    myController.getGame().forfeitHandler(myController.getGame().getCurrPlayer());
                    myController.getGame().startNextTurn();
                    myDiceRoller.setDisable(true);
                    endTurnButton.setDisable(false);
                }
            }
        });

//        HBox forfeitAndMove = addForfeitAndMove();
        HBox nameAndEnd = new HBox(Popup.PADDING_TWENTY);
        nameAndEnd.setAlignment(Pos.CENTER_LEFT);
        Text playerName = new Text(myPlayer.getName());
        Node playerIcon = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(myPlayer.getImage()),
                Board.TOKEN_SPACING,Board.TOKEN_SPACING,false,true));
        bailButton = new Button("Pay Bail");
        bailButton.setOnAction(e -> {
            myPlayer.payBail(myController.getGame().getBank());
            Alert bailAlert = new Alert(Alert.AlertType.INFORMATION);
            bailAlert.setContentText("You paid bail of $"+myController.getGame().getJailBail()+
                    ".\nRoll dice to move out of jail.");
            bailAlert.show();
            bailButton.setVisible(false);
        });
        if(!myPlayer.isInJail()){
            bailButton.setVisible(false);
        }
        nameAndEnd.getChildren().addAll(playerIcon,playerName,endTurnButton,bailButton);

        myVBox.getChildren().addAll(nameAndEnd,createBalanceText(),
                createAssetsListView(),createActionCardsListView());//,addForfeitAndMove());
        return myVBox;
    }

    protected HBox addForfeitAndMove(){

        HBox moveBox = new HBox();
        TextField moveTo = new TextField();
        moveTo.setPrefWidth(80);
        Button move = new Button("MOVE");
        moveBox.getChildren().addAll(moveTo,move);
        move.setOnAction(e -> myController.getGame().movePlayer(myPlayer.getCurrentLocation(),Integer.parseInt(moveTo.getText())));

        Button forfeit = new Button("FORFEIT");
        forfeit.setId("button1");
        forfeit.setOnAction(e-> {
            myController.getGame().forfeitHandler(myController.getGame().getCurrPlayer());
//            myController.getGame().getPlayers().remove(myController.getGame().getCurrPlayer());
            myController.getGame().startNextTurn();
            myDiceRoller.setDisable(false);
        });

        HBox forfeitAndMove = new HBox(10);
        forfeitAndMove.getChildren().addAll(forfeit,moveBox);
        return forfeitAndMove;
    }

    private ListView createAssetsListView(){
        ListView<Property> assetsListView = new ListView<>(myPlayer.getProperties());
        assetsListView.setCellFactory(list -> new PropertyCell());
        return assetsListView;
    }

    private ListView createActionCardsListView(){
        ListView<AbstractActionCard> cardsListView = new ListView<>(myPlayer.getActionCards());
        cardsListView.setOrientation(Orientation.HORIZONTAL);
        cardsListView.setCellFactory(list -> new ActionCardCell(myController.getGame()));
        return cardsListView;
    }

    private Text createBalanceText(){
        myFunds = new Text("$ "+myPlayer.getFunds());
        return myFunds;
    }

    public AnchorPane getPlayerControlView(){
        return myAnchorPane;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        myFunds.setText("$ "+ myPlayer.getFunds());
    }

    public VBox getMyVBox() {
        return myVBox;
    }

}
