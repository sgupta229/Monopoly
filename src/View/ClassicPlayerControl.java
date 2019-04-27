package View;

import Controller.Controller;
import Model.AbstractPlayer;
import View.PopUps.BuildOrSellPopup;
import View.PopUps.Popup;
import View.PopUps.TradePopup;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ClassicPlayerControl extends PlayerControl {

    private Controller myController;

    public ClassicPlayerControl(AbstractPlayer player, Controller controller){
        super(player, controller);
        this.myController = controller;

        getMyVBox().getChildren().addAll();
        HBox manageTradeBox = new HBox(Popup.HBOX_SPACING_TEN);
        Button manageProperty = new Button("Manage Property");
        manageProperty.setOnAction(e -> new BuildOrSellPopup(myController).display());
        Button trade = new Button("Trade");
        trade.setOnAction(e -> new TradePopup(myPlayer,myController.getGame().getPlayers(),myController.getGame()).display());
        manageTradeBox.getChildren().addAll(manageProperty,trade);


        getMyVBox().getChildren().addAll(manageTradeBox);
        getMyVBox().getChildren().addAll(addForfeitAndMove());
    }

}
