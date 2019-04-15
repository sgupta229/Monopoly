package View;

import Controller.Controller;
import Model.AbstractPlayer;
import Model.properties.ColorProperty;
import Model.properties.Property;
import View.PopUps.BuildOrSellPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class PlayerControl implements PropertyChangeListener {
    protected Controller myController;
    protected AbstractPlayer myPlayer;

    protected AnchorPane myAnchorPane;
    protected VBox myVBox;
    protected DiceRoller myDiceRoller;
    protected Text myFunds;


    public PlayerControl(AbstractPlayer player, Controller controller){
        myPlayer = player;
        myController = controller;
        myPlayer.addPropertyChangeListener("funds",this);

        setUpLayout();
    }

    private void setUpLayout(){
        myAnchorPane = new AnchorPane();

        myDiceRoller = new DiceRoller(myController);
        HBox diceRollerView = myDiceRoller.getDiceRollerView();

        myAnchorPane.getChildren().addAll(createVBox(),diceRollerView);
        AnchorPane.setTopAnchor(myVBox,20.0);
        AnchorPane.setBottomAnchor(diceRollerView,20.0);
    }

    private VBox createVBox(){
        myVBox = new VBox();

        //TODO: move magic val to properties
        Button endTurnButton = new Button("END TURN");
        endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                myController.getGame().startNextTurn();
                myDiceRoller.setDisable(false);
            }
        });

        Button manageProperty = new Button("Manage Property");
        manageProperty.setOnAction(e -> new BuildOrSellPopup( 39, myController).display());

        HBox moveBox = new HBox();
        TextField moveTo = new TextField();
        Button move = new Button("MOVE");
        moveBox.getChildren().addAll(moveTo,move);
        move.setOnAction(e -> myController.getGame().movePlayer(myController.getGame().getCurrPlayer().getCurrentLocation(), Integer.parseInt(moveTo.getText())));


        //TODO game.movePlayer(curr.getcurrentloc, new ind)

        myVBox.setId("playerControlBox");
        HBox nameAndEnd = new HBox(20);
        nameAndEnd.setAlignment(Pos.CENTER_LEFT);
        Text playerName = new Text(myPlayer.getName());
        ImageView playerIcon = myController.getPlayerImageView(myPlayer);
        nameAndEnd.getChildren().addAll(playerIcon,playerName,endTurnButton);
        myVBox.getChildren().addAll(nameAndEnd,createBalanceText(), moveBox,manageProperty,createAssetsListView());
        return myVBox;
    }

    private ListView createAssetsListView(){
        ArrayList<Property> temp = new ArrayList<>();
        ArrayList<Double> fakeVals = new ArrayList<>();
        for (int i=0;i<8;i++){
            fakeVals.add(i+0.5);
        }
        temp.add(new ColorProperty(10, "Color Test", "GREEN", fakeVals));

        ObservableList<Property> assetsList = FXCollections.observableArrayList(temp);
        ListView<Property> assetsListView = new ListView<>(assetsList);

        assetsListView.setCellFactory(new Callback<ListView<Property>, ListCell<Property>>() {
            @Override
            public ListCell<Property> call(ListView<Property> list) {
                return new PropertyCell();
            }
        });
        return assetsListView;
    }

    private Text createBalanceText(){
        myFunds = new Text("$ "+myPlayer.getFunds());
        return myFunds;
    }

    public AnchorPane getPlayerControlView(){
        return myAnchorPane;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("funds event received");
//        Double newFunds = (Double) evt.getNewValue();
//        myFunds = new Text("$ "+newFunds);
        myVBox.getChildren().set(1,createBalanceText());
    }

}
