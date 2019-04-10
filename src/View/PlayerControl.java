package View;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class PlayerControl {
    protected AnchorPane myAnchorPane;
    protected VBox myVBox;
    protected DiceRoller myDiceRoller;
    protected Controller myController;

    protected AbstractPlayer myPlayer;

    public PlayerControl(AbstractPlayer player){
        myPlayer = player;

        myAnchorPane = new AnchorPane();
        myVBox = new VBox();

//        Text playerName = new Text(player.getName());
        Text playerName = new Text("player name");
        myVBox.getChildren().addAll(playerName);

        myDiceRoller = new DiceRoller(myController);
        HBox diceRollerView = myDiceRoller.getDiceRollerView();

        myAnchorPane.getChildren().addAll(myVBox,diceRollerView);
        AnchorPane.setTopAnchor(myVBox,20.0);
        AnchorPane.setBottomAnchor(diceRollerView,20.0);
    }

    //temporary until concrete player is made
    public PlayerControl(Controller controller){
        myController = controller;
        myAnchorPane = new AnchorPane();
        myVBox = new VBox();

        Text playerName = new Text("player name");
        //TODO: move magic val to properties
        Button endTurnButton = new Button("END TURN");
        endTurnButton.setOnAction(e->myController.getGame().startNextTurn());
        myVBox.getChildren().addAll(endTurnButton);

        myDiceRoller = new DiceRoller(myController);
        HBox diceRollerView = myDiceRoller.getDiceRollerView();

        myAnchorPane.getChildren().addAll(myVBox,diceRollerView);
        AnchorPane.setTopAnchor(myVBox,20.0);
        AnchorPane.setBottomAnchor(diceRollerView,20.0);
    }

    public AnchorPane getPlayerControlView(){
        return myAnchorPane;
    }

}
