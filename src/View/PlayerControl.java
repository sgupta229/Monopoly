package View;

import Model.AbstractPlayer;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class PlayerControl {
    protected VBox myVBox;
    protected AbstractPlayer myPlayer;

    public PlayerControl(AbstractPlayer player){
        myVBox = new VBox();
        myPlayer = player;
    }

    public VBox getPlayerControlView(){
        return myVBox;
    }

}
