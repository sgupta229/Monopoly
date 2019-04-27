package View;

import Controller.AbstractGame;
import View.PopUps.Popup;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ResourceBundle;

public class RulesTab {

    AbstractGame myGame;

    VBox myLayout = new VBox(Popup.PADDING_TWENTY);

    public RulesTab (AbstractGame game){
        myGame = game;
        ResourceBundle myRules = ResourceBundle.getBundle("rules");

        Label label = new Label(myRules.getString("title"));
        int bankFunds = new BigDecimal(myGame.getBankFunds()).intValue();

        Label evenBuilding = new Label(myRules.getString("evenBuilding")+ (myGame.getEvenBuildingRule()));
        Label freeParking = new Label(myRules.getString("freeParking") + (myGame.getFreeParkingRule()));
        Label landOnGo = new Label(myRules.getString("passGo")+ (myGame.getLandOnGoMult()));
        Label rollsInJail = new Label(myRules.getString("rollsJail")+ (myGame.getRollsInJailRule()) + myRules.getString("rollsJail2"));
        Label startFunds = new Label(myRules.getString("startingFunds")+(myGame.getStartFunds()));
        Label jailBail = new Label(myRules.getString("jailBail")+(myGame.getJailBail()) + myRules.getString("jailBail2"));
        Label passGo = new Label(myRules.getString("go")+ (myGame.getPassGo()) + myRules.getString("go2"));
        Label snakeEyes = new Label(myRules.getString("snakeEyes")+(myGame.getSnakeEyes())+ myRules.getString("snakeEyes2"));
        myLayout.getChildren().addAll(label, startFunds,new Label(myRules.getString("bankFunds")+ bankFunds ),evenBuilding,freeParking,landOnGo,rollsInJail,jailBail,passGo,snakeEyes);
    }

    public VBox getView(){
        return myLayout;
    }

}
