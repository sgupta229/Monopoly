package View;
import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class ChooseGameScreen {
    private Controller myController;

    private static final String LOGO_PATH = "logo.png";

    private Scene myScene;
    private BorderPane myLogoPane;
    private ResourceBundle myResourceBundle;
    private List<String> gameTypeButtons;
    private Group myRoot;
    private FlowPane myFlowPane;


    public ChooseGameScreen(double width, double height, String style, Controller controller) {
        myResourceBundle = ResourceBundle.getBundle("GameTypes");
        this.myRoot = new Group();
        this.myController = controller;

        myScene = new Scene(myRoot, width, height, Color.WHITE);
        myScene.getStylesheets().add(style);

        createMonopolyLogo();
        addFlowPane();
        myRoot.getChildren().addAll(myLogoPane, myFlowPane);
    }
    public Scene getScene(){
        return myScene;
    }

    private Button createButton(String text) {
        Button button = new Button(myResourceBundle.getString(text));
        button.setId("button1");
        button.setOnAction(new ButtonHandler(text));
        return button;
    }

    class ButtonHandler implements EventHandler<ActionEvent> {
        private final String gameType;

        ButtonHandler(String game) {
            this.gameType = game;
        }
        @Override
        public void handle(ActionEvent event) {
            System.out.print(gameType);
            myController.setGameAndGoToAddPlayers(gameType);
        }
    }

    private FlowPane addFlowPane() {
        myFlowPane = new FlowPane();
        myFlowPane.setVgap(50);
        myFlowPane.setHgap(50);
        myFlowPane.setLayoutX(Controller.WIDTH/7.5);
        myFlowPane.setLayoutY(Controller.HEIGHT/2);
        myFlowPane.setPrefWrapLength(Controller.WIDTH);
        myFlowPane.setId("flowPane");

        gameTypeButtons = new ArrayList<>(Arrays.asList("classic", "mega", "junior", "starWars"));

        for (String type : gameTypeButtons) {
            myFlowPane.getChildren().add(createButton(type));
        }
        return myFlowPane;
    }

    private BorderPane createMonopolyLogo(){
        myLogoPane = new BorderPane();
        myLogoPane.setPrefSize(Controller.WIDTH, Controller.HEIGHT/1.5);
        var logo = new Image(this.getClass().getClassLoader().getResourceAsStream(LOGO_PATH));
        myLogoPane.setCenter(new ImageView(logo));
        return myLogoPane;
    }

}
