package View;

import Controller.Controller;
import Model.AbstractPlayer;
import Model.properties.Property;
import View.PopUps.BuildOrSellPopup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

public abstract class PlayerControl implements PropertyChangeListener {
    protected Controller myController;
    protected AbstractPlayer myPlayer;
    protected ResourceBundle messages;

    protected AnchorPane myAnchorPane;
    protected VBox myVBox;
    protected DiceRoller myDiceRoller;
    protected Text myFunds;


    public PlayerControl(AbstractPlayer player, Controller controller){
        myPlayer = player;
        myController = controller;
        myPlayer.addPropertyChangeListener("funds",this);

        messages = ResourceBundle.getBundle("Messages");

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

        Button endTurnButton = new Button(messages.getString("end-turn"));
        endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                myController.getGame().startNextTurn();
                myDiceRoller.setDisable(false);
            }
        });

        Button manageProperty = new Button("Manage Property");
        manageProperty.setOnAction(e -> new BuildOrSellPopup(39,myController).display());

        HBox moveBox = new HBox();
        TextField moveTo = new TextField();
        Button move = new Button("MOVE");
        moveBox.getChildren().addAll(moveTo,move);
        move.setOnAction(e -> myController.getGame().movePlayer(myPlayer.getCurrentLocation(),Integer.parseInt(moveTo.getText())));

        Button forfeit = new Button("FORFEIT");
        forfeit.setId("button1");
        forfeit.setOnAction(e-> {
            myController.getGame().getPlayers().remove(myController.getGame().getCurrPlayer());
            myController.getGame().startNextTurn();
            myDiceRoller.setDisable(false);
        });

        myVBox.setId("playerControlBox");
        HBox nameAndEnd = new HBox(20);
        nameAndEnd.setAlignment(Pos.CENTER_LEFT);
        Text playerName = new Text(myPlayer.getName());
        Node playerIcon = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(myPlayer.getImage()),
                40.0,40.0,false,true));
        nameAndEnd.getChildren().addAll(playerIcon,playerName,endTurnButton);
        myVBox.getChildren().addAll(nameAndEnd,createBalanceText(), moveBox,manageProperty,createAssetsListView(),forfeit);
        return myVBox;
    }

    private ListView createAssetsListView(){
        ListView<Property> assetsListView = new ListView<>(myPlayer.getProperties());

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
        myFunds.setText("$ "+Double.toString(myPlayer.getFunds()));
    }

}
