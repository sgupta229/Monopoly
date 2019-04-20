package View;
import Controller.Controller;
import Controller.AbstractGame;
import Controller.GameSaver;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class EndGameScreen {
    private Controller myController;
    private Scene myScene;
    private ResourceBundle messages;
    private Group myRoot;
    private FlowPane myFlowPane;


    public EndGameScreen(double width, double height, String style, Controller controller, AbstractPlayer winner) {
        messages = ResourceBundle.getBundle("Messages");
        this.myRoot = new Group();
        this.myController = controller;
        myScene = new Scene(myRoot, width, height, Color.WHITE);
        myScene.getStylesheets().add(style);
        addFlowPane(winner.getName());
        myRoot.getChildren().addAll(myFlowPane);
    }
    public Scene getScene(){
        return myScene;
    }

    private FlowPane addFlowPane(String winner) {
        myFlowPane = new FlowPane();
        myFlowPane.setVgap(50);
        myFlowPane.setHgap(50);
        myFlowPane.setLayoutX(Controller.WIDTH/7.5);
        myFlowPane.setLayoutY(Controller.HEIGHT/2);
        myFlowPane.setPrefWrapLength(Controller.WIDTH);
        myFlowPane.setId("flowPane");
        Button button = new Button(messages.getString("newGame"));
        button.setId("button1");
        myFlowPane.getChildren().addAll(createMessage(winner),button);
        button.setOnAction(e -> myController.goToChooseGameScreen());

        return myFlowPane;
    }

    private Label createMessage(String winner){
        Label myLabel = new Label(messages.getString("end-game-congrats") + winner+messages.getString("end-game-message") );
        return myLabel;
    }


}