package View;

import Controller.Controller;
import Model.AbstractPlayer;
import View.PopUps.BuildOrSellPopup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class PlayerControl implements PropertyChangeListener {
    protected Controller myController;
    protected AbstractPlayer myPlayer;

    protected AnchorPane myAnchorPane;
    protected VBox myVBox;
    protected DiceRoller myDiceRoller;
    protected Text myFunds;


    public PlayerControl(AbstractPlayer player, Controller controller){
        myPlayer = player;
        myController = controller;
        myPlayer.addPropertyChangeListener("funds",this);

        setUpLayout();
    }

    private void setUpLayout(){
        myAnchorPane = new AnchorPane();

        myDiceRoller = new DiceRoller(myController);
        HBox diceRollerView = myDiceRoller.getDiceRollerView();

        myAnchorPane.getChildren().addAll(createVBox(),diceRollerView);
        AnchorPane.setTopAnchor(myVBox,20.0);
        AnchorPane.setBottomAnchor(diceRollerView,20.0);
    }

    private VBox createVBox(){
        myVBox = new VBox();

        //TODO: move magic val to properties
        Button endTurnButton = new Button("END TURN");
        endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                myController.getGame().startNextTurn();
                myDiceRoller.setDisable(false);
            }
        });

        Button manageProperty = new Button("Manage Property");
        manageProperty.setOnAction(e -> new BuildOrSellPopup("Manage Property", 39, myController).display());

        HBox moveBox = new HBox();
        TextField moveTo = new TextField();
        Button move = new Button("MOVE");
        moveBox.getChildren().addAll(moveTo,move);
        move.setOnAction(e -> myController.getGame().movePlayer(myController.getGame().getCurrPlayer().getCurrentLocation(), Integer.parseInt(moveTo.getText())));

        //TODO game.movePlayer(curr.getcurrentloc, new ind)

        myVBox.setId("playerControlBox");
        HBox nameAndEnd = new HBox(50);
        Text playerName = new Text(myPlayer.getName());
        nameAndEnd.getChildren().addAll(playerName,endTurnButton);
        myVBox.getChildren().addAll(nameAndEnd,createBalanceText(), moveBox,manageProperty);
        return myVBox;
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
        Double newFunds = (Double) evt.getNewValue();
        myFunds = new Text("$ "+Double.toString(newFunds));
        myVBox.getChildren().set(2,myFunds);
    }

}
