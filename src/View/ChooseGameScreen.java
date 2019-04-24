package View;
import Controller.Controller;
import Controller.GameSaver;
import Controller.ClassicGame;
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

import java.io.File;
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
    private ResourceBundle messages;
    private List<String> gameTypeButtons;
    private Group myRoot;
    private FlowPane myFlowPane;
    private HBox loadHBox;
    private Button loadButton;
    private Button goButton;
    private Label chosenFile;
    private File openedFile;


    public ChooseGameScreen(double width, double height, String style, Controller controller) {
        myResourceBundle = ResourceBundle.getBundle("GameTypes");
        messages = ResourceBundle.getBundle("Messages");
        this.myRoot = new Group();
        this.myController = controller;

        myScene = new Scene(myRoot, width, height, Color.WHITE);
        myScene.getStylesheets().add(style);

        createMonopolyLogo();
        addFlowPane();
        createLoadHBox();
        myRoot.getChildren().addAll(myLogoPane, myFlowPane, loadHBox);
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
            myController.setGame(gameType);
            myController.goToAddPlayersScreen();
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

        gameTypeButtons = new ArrayList<>(Arrays.asList("classic", "tropical", "junior"));

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

    private void createLoadHBox(){
        loadHBox = new HBox(10);

        loadButton = new Button(messages.getString("load"));
        loadButton.setOnAction(new LoadButtonHandler());

        goButton = new Button(messages.getString("go"));
        goButton.setDisable(true);
        goButton.setOnAction(new GoButtonHandler());

        chosenFile = new Label("");

        loadHBox.getChildren().addAll(loadButton,goButton,chosenFile);
    }

    private class LoadButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(messages.getString("choose-saved-game"));
//            fileChooser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter("Serializable", "*.ser"));
//            fileChooser.setInitialFileName(".properties");
            openedFile = fileChooser.showOpenDialog(myController.getStage());
            chosenFile.setText(openedFile.getName());
            if (openedFile != null) goButton.setDisable(false);
        }
    }

    private class GoButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            //TODO: fix to create GameSaver of dynamic game type
            GameSaver<ClassicGame> mySaver = new GameSaver<ClassicGame>();
            ClassicGame newGame = mySaver.reload(openedFile);
            myController.setGame(newGame);
        }
    }
}
