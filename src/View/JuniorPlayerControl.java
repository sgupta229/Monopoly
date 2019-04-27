package View;

import Controller.Controller;
import Model.AbstractPlayer;

public class JuniorPlayerControl extends PlayerControl {

    public JuniorPlayerControl(AbstractPlayer player, Controller controller) {
        super(player, controller);
        getMyVBox().getChildren().addAll();
        getMyVBox().getChildren().addAll(addForfeitAndMove());

    }
}
