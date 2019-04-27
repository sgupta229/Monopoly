package View;

import Controller.AbstractGame;
import Controller.Controller;
import Model.AbstractPlayer;
import Model.properties.Property;
import View.PopUps.Popup;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;
import java.util.List;

public class RespondToTradePopup {
    private ResourceBundle messages;
    private Stage myStage;
    private Pane myLayout;
    private AbstractPlayer currentPlayer;
    private AbstractPlayer otherPlayer;
    private AbstractGame myGame;

    private Set<Property> currentPlayerOffers;
    private Set<Property> currentPlayerWants;

    public RespondToTradePopup(Stage stage, AbstractPlayer current, AbstractPlayer other,
                               Set<Property> offers, Set<Property> wants, AbstractGame game){
        this.messages = ResourceBundle.getBundle("Messages");
        this.myStage = stage;
        this.currentPlayer = current;
        this.otherPlayer = other;
        this.currentPlayerOffers = offers;
        this.currentPlayerWants = wants;
        this.myGame = game;

        this.myLayout = new Pane();

        setUpLayout();
    }

    public void setUpLayout(){
        VBox vBox = new VBox();
        Label currentPlayerName = new Label(currentPlayer.getName());
        Text text1 = new Text("wants to trade");

        List<String> offersStrings = new ArrayList<>();
        for (Property p:currentPlayerOffers){
            offersStrings.add(p.getName());
        }
        String offers = String.join(", ",offersStrings);
        Text offersText = new Text(offers);

        Text text2 = new Text("for your properties: ");
        List<String> wantsStrings = new ArrayList<>();
        for (Property p:currentPlayerWants){
            wantsStrings.add(p.getName());
        }
        String wants = String.join(", ",wantsStrings);
        Text wantsText = new Text(wants);

        HBox buttons = new HBox();
        Button make = new Button("Make Trade");
        make.setOnAction(new MakeTradeHandler());
        Button decline = new Button("Decline Trade");
        decline.setOnAction(e->{
            myStage.close();
        });
        buttons.getChildren().addAll(make,decline);

        vBox.getChildren().addAll(currentPlayerName,text1,offersText,text2,wantsText,buttons);
        myLayout.getChildren().addAll(vBox);
    }

    public void display(){
        Scene scene = new Scene(myLayout, Controller.WIDTH/ Popup.V_BOX_SPACING, Controller.HEIGHT/Popup.IMAGE_HEIGHT_SPACING);
        scene.getStylesheets().add(( new File("data/GUI.css") ).toURI().toString());
        myStage.setScene(scene);
        myStage.show();
    }

    private class MakeTradeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (otherPlayer == null) return;

            Map<AbstractPlayer,List<Property>> trade = new HashMap<>();
            trade.put(currentPlayer,new ArrayList<>(currentPlayerOffers));
            trade.put(otherPlayer, new ArrayList<>(currentPlayerWants));
            myGame.completeTrade(trade);
            myStage.close();
        }
    }
}
