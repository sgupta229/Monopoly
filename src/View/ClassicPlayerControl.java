package View;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.scene.text.Text;

public class ClassicPlayerControl extends PlayerControl {

    public ClassicPlayerControl(AbstractPlayer player){
        super(player);

        Text t = new Text("This is a player control tab");
        myVBox.getChildren().addAll(t);
    }

    //temporary until concrete Player is made
    public ClassicPlayerControl(Controller controller){
        super(controller);

        Text t = new Text("This is a player control tab");
        for (AbstractPlayer p : controller.getPlayers()){
            System.out.println(p.getName());
        }
        myVBox.getChildren().addAll(t);
        for (AbstractPlayer p : controller.getPlayers()){
            myVBox.getChildren().add(controller.getPlayerImageView(p));
        }

    }
}
