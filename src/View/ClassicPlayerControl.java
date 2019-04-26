package View;

import Controller.Controller;
import Model.AbstractPlayer;

public class ClassicPlayerControl extends PlayerControl {

    public ClassicPlayerControl(AbstractPlayer player, Controller controller){
        super(player, controller);

        getMyVBox().getChildren().addAll();
    }

}
