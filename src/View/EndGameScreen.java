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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
    private Scene myScene;
    private ResourceBundle messages;
    private Group myRoot;
    private VBox myFlowPane;


    public EndGameScreen(double width, double height, String style, AbstractPlayer winner, Stage window) {
        messages = ResourceBundle.getBundle("Messages");
        this.myRoot = new Group();
        myScene = new Scene(myRoot, width, height, Color.WHITE);
        myScene.getStylesheets().add(style);
        addFlowPane(winner.getName(),window);
        Image img = new Image("winner.jpg");
        ImagePattern pattern = new ImagePattern(img);
        myScene.setFill(pattern);
        myRoot.getChildren().addAll(myFlowPane);
    }
    public Scene getScene(){
        return myScene;
    }

    private VBox addFlowPane(String winner, Stage window) {
        myFlowPane = new VBox(20);
        myFlowPane.setLayoutX(Controller.WIDTH/8);
        myFlowPane.setLayoutY(Controller.HEIGHT/3);
        myFlowPane.setId("flowPane");
        Button button = new Button(messages.getString("newGame"));
        button.setId("button3");
        myFlowPane.getChildren().addAll(createMessage(winner),button);
        Stage newStage = new Stage();
        Controller newController = new Controller(newStage);
        button.setOnAction(e -> {
            newController.goToChooseGameScreen();
            window.close();
        });

        return myFlowPane;
    }

    private Label createMessage(String winner){
        Label myLabel = new Label(messages.getString("end-game-congrats") + " " + winner + " "+messages.getString("end-game-message") );
        return myLabel;
    }


}