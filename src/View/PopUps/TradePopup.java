package View.PopUps;

import Controller.AbstractGame;
import Controller.Controller;
import Model.AbstractPlayer;
import Model.properties.Property;
import View.AbstractPlayerCell;
import View.RespondToTradePopup;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.util.*;

public class TradePopup {
    private ResourceBundle messages;
    private Stage myStage;
    private Pane myLayout;
    private AbstractPlayer currentPlayer;
    private AbstractPlayer otherPlayer;
    private ObservableList<AbstractPlayer> allPlayers;
    private AbstractGame myGame;

    private Set<Property> currentPlayerOffers;
    private Set<Property> currentPlayerWants;
    private VBox currPlayerTradeBox;
    private VBox otherPlayerTradeBox;
    private Pane otherPlayerPane;

    public TradePopup(AbstractPlayer player, ObservableList<AbstractPlayer> players, AbstractGame game){
        this.messages = ResourceBundle.getBundle("Messages");
        this.myStage = new Stage();
        this.currentPlayer = player;
        this.allPlayers = players;
        this.myGame = game;
        this.myLayout = new Pane();

        this.currentPlayerOffers = new HashSet<>();
        this.currentPlayerWants = new HashSet<>();
        this.otherPlayerPane = new Pane();

        setUpLayout();

    }

    private void setUpLayout(){
        VBox myVBox = new VBox();
        HBox myHBox = new HBox();

        currPlayerTradeBox = createPlayerTradeBox(currentPlayer,currentPlayerOffers);

        List<AbstractPlayer> copy = new ArrayList<>();
        for (AbstractPlayer p : allPlayers){
            copy.add(p);
        }
        ObservableList<AbstractPlayer> allPlayersExceptCurrent = FXCollections.observableList(copy);
        allPlayersExceptCurrent.remove(currentPlayer);
        ComboBox<AbstractPlayer> players = new ComboBox<>(allPlayersExceptCurrent);
        players.setButtonCell(new PlayerCell());
        players.setCellFactory(list -> new PlayerCell());
        players.setOnAction( e -> {
            playerSelectedChanged(players.getValue());
        });

        Button propose = new Button("Propose Trade");
        propose.setOnAction(new ProposeButtonHandler());

        myHBox.getChildren().addAll(currPlayerTradeBox,otherPlayerPane);
        myVBox.getChildren().addAll(players,myHBox,propose);
        myLayout.getChildren().addAll(myVBox);
    }

    private class PlayerCell extends ListCell<AbstractPlayer> {
        public PlayerCell(){
            if (getItem() != null) setText(getItem().getName());
        }
        @Override protected void updateItem(AbstractPlayer item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.getName());
            }
        }
    }

    private void playerSelectedChanged(AbstractPlayer selectedPlayer){
        if (selectedPlayer == null) {
            otherPlayerPane.getChildren().clear();
            System.out.println("null chosen");
            otherPlayer = null;
            return;
        }
        System.out.println("player chosen: "+selectedPlayer.getName());
        otherPlayer = selectedPlayer;
        currentPlayerWants.clear();
        otherPlayerPane.getChildren().clear();
        otherPlayerTradeBox = createPlayerTradeBox(selectedPlayer,currentPlayerWants);
        otherPlayerPane.getChildren().addAll(otherPlayerTradeBox);
    }

    private VBox createPlayerTradeBox(AbstractPlayer player, Set<Property> propSet){
        VBox vBox = new VBox();
        vBox.setId("box");

        Label playerName = new Label(player.getName());
        List<Property> playerProperties = player.getProperties();

        CheckBox[] checkBoxes = new CheckBox[playerProperties.size()];
        VBox propsBox = new VBox();
        for (int i=0; i<playerProperties.size(); i++){
            Property prop = playerProperties.get(i);
            CheckBox checkBox = checkBoxes[i] = new CheckBox(prop.getName());
            propsBox.getChildren().addAll(checkBox);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                                    Boolean old_val, Boolean new_val) {
                    if(new_val == true) propSet.add(prop);
                    if(new_val == false) propSet.remove(prop);
                }
            });
        }

        vBox.getChildren().addAll(playerName,propsBox);
        return vBox;
    }

    private class ProposeButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            if (otherPlayer == null) return;

            new RespondToTradePopup(myStage,currentPlayer,otherPlayer,currentPlayerOffers,currentPlayerWants,myGame).display();
        }
    }

    public void display(){
        Scene scene = new Scene(myLayout, Controller.WIDTH/Popup.V_BOX_SPACING, Controller.HEIGHT/Popup.IMAGE_HEIGHT_SPACING);
        scene.getStylesheets().add(( new File("data/GUI.css") ).toURI().toString());
        myStage.setScene(scene);
        myStage.show();
    }

}
