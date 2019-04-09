package View;

import Model.AbstractPlayer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class PlayerControl {
    protected AnchorPane myAnchorPane;
    protected VBox myVBox;
    protected DiceRoller myDiceRoller;

    protected AbstractPlayer myPlayer;

    public PlayerControl(AbstractPlayer player){
        myPlayer = player;

        myAnchorPane = new AnchorPane();
        myVBox = new VBox();

//        Text playerName = new Text(player.getName());
        Text playerName = new Text("player name");
        myVBox.getChildren().addAll(playerName);

        myDiceRoller = new DiceRoller();
        HBox diceRollerView = myDiceRoller.getDiceRollerView();

        myAnchorPane.getChildren().addAll(myVBox,diceRollerView);
        AnchorPane.setTopAnchor(myVBox,20.0);
        AnchorPane.setBottomAnchor(diceRollerView,20.0);
    }

    //temporary until concrete player is made
    public PlayerControl(){
        myAnchorPane = new AnchorPane();
        myVBox = new VBox();

        Text playerName = new Text("player name");
        myVBox.getChildren().addAll(playerName);

        myDiceRoller = new DiceRoller();
        HBox diceRollerView = myDiceRoller.getDiceRollerView();

        myAnchorPane.getChildren().addAll(myVBox,diceRollerView);
        AnchorPane.setTopAnchor(myVBox,20.0);
        AnchorPane.setBottomAnchor(diceRollerView,20.0);
    }

    public AnchorPane getPlayerControlView(){
        return myAnchorPane;
    }

}
