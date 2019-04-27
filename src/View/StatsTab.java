package View;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.properties.BuildingType;
import Model.properties.Property;
import View.PopUps.Popup;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class StatsTab {
    AbstractGame myGame;

    VBox myLayout = new VBox(Popup.PADDING_TWENTY);

    StatsTab(AbstractGame game){
        myGame = game;

        Label label = new Label("stats");

        BigDecimal bankFunds = new BigDecimal(game.getBankFunds());
        myLayout.getChildren().addAll(label, new Label("BANK FUNDS: $" + bankFunds.toPlainString()));
        updateStatsTab();
    }

    public VBox getView(){
        return myLayout;
    }


    public void updateStatsTab(){
        for (AbstractPlayer player : myGame.getPlayers()){
            VBox myStats = new VBox();
            myStats.getChildren().add(new Label(player.getName()+"'s Stats:"));
            for (Property prop : player.getProperties()){
                myStats.getChildren().addAll(new Label(prop.getName()+ "has " + prop.getNumBuilding(BuildingType.HOTEL) + " house(s) and " + prop.getNumBuilding(BuildingType.HOTEL) + " hotel(s)."));
                System.out.print(player.getName() + prop.getName());
            }
            myLayout.getChildren().add(myStats);
        }
    }

}
