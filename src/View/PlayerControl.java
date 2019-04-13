package View;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        myVBox.setId("playerControlBox");
        Text playerName = new Text(myPlayer.getName());
        Button endTurnButton = new Button("END TURN");
        endTurnButton.setOnAction(e->myController.getGame().startNextTurn());
        myVBox.getChildren().addAll(playerName,endTurnButton,createBalanceText());
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
