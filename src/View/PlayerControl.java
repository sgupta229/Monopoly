package View;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
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

    public PlayerControl(AbstractPlayer player, Controller controller){
        myPlayer = player;
        myController = controller;

        setUpLayout();
    }

    private void setUpLayout(){
        myAnchorPane = new AnchorPane();

        myVBox = new VBox();
        Text playerName = new Text(myPlayer.getName());
        Button endTurnButton = new Button("END TURN");
        endTurnButton.setOnAction(e->myController.getGame().startNextTurn());
        myVBox.getChildren().addAll(playerName,endTurnButton);

        myDiceRoller = new DiceRoller(myController);
        HBox diceRollerView = myDiceRoller.getDiceRollerView();

        myAnchorPane.getChildren().addAll(myVBox,diceRollerView);
        AnchorPane.setTopAnchor(myVBox,20.0);
        AnchorPane.setBottomAnchor(diceRollerView,20.0);
    }

    //temporary until concrete player is made
//    public PlayerControl(Controller controller, Board board){
//        myController = controller;
//        myAnchorPane = new AnchorPane();
//        myVBox = new VBox();
//
//        Text playerName = new Text("player name");
//        //TODO: move magic val to properties
//        Button endTurnButton = new Button("END TURN");
//        endTurnButton.setOnAction(e->myController.getGame().startNextTurn());
//        myVBox.getChildren().addAll(endTurnButton);
//
//        myDiceRoller = new DiceRoller(myController,board);
//        HBox diceRollerView = myDiceRoller.getDiceRollerView();
//
//        myAnchorPane.getChildren().addAll(myVBox,diceRollerView);
//        AnchorPane.setTopAnchor(myVBox,20.0);
//        AnchorPane.setBottomAnchor(diceRollerView,20.0);
//    }

    public AnchorPane getPlayerControlView(){
        return myAnchorPane;
    }

}
