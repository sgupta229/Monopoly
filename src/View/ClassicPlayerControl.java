package View;

import Model.AbstractPlayer;
import javafx.scene.text.Text;

public class ClassicPlayerControl extends PlayerControl {

    public ClassicPlayerControl(AbstractPlayer player){
        super(player);

        Text t = new Text("This is a player control tab");
        myVBox.getChildren().addAll(t);
    }
}
