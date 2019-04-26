package View;
import Controller.Controller;
import Model.AbstractPlayer;
import View.PopUps.Popup;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
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
        myFlowPane = new VBox(Popup.PADDING_TWENTY);
        myFlowPane.setLayoutX(Controller.WIDTH/8);
        myFlowPane.setLayoutY(Controller.HEIGHT/Popup.VBOX_SPACING);
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