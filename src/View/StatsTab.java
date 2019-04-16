package View;

import Model.Bank;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class StatsTab {
    Bank myBank;

    Pane myLayout = new Pane();

    StatsTab(Bank bank){
        myBank = bank;

        Label label = new Label("stats");

        myLayout.getChildren().addAll(label);

    }

    public Pane getView(){
        return myLayout;
    }

}
