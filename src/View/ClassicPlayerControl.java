package View;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.scene.text.Text;

public class ClassicPlayerControl extends PlayerControl {

    public ClassicPlayerControl(AbstractPlayer player){
        super(player);

        Text t = new Text("insert view of properties and player actions here");
        myVBox.getChildren().addAll(t);
    }

    //temporary until concrete Player is made
    public ClassicPlayerControl(Controller controller){
        super(controller);

        Text t = new Text("insert view of properties and player actions here");
        for (AbstractPlayer p : controller.getPlayers()){
            System.out.println(p.getName());
        }
        myVBox.getChildren().addAll(t);
        for (AbstractPlayer p : controller.getPlayers()){
            myVBox.getChildren().add(controller.getPlayerImageView(p));
        }

    }
}
