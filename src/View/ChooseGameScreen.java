package View;
import Controller.Controller;
import Controller.ClassicGame;
import Model.XmlReaderException;
import View.PopUps.Popup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Enumeration;
import java.util.ResourceBundle;
import Controller.SaveGame;


public class ChooseGameScreen {
    private Controller myController;

    private Scene myScene;
    private ResourceBundle gameTypeNames;
    private ResourceBundle messages;
    private Enumeration<String> gameTypeNamesKeys;
    private Group myRoot;
    private FlowPane myFlowPane;
    private HBox loadHBox;
    private Button loadButton;
    private Button goButton;
    private Label chosenFile;
    private File openedFile;


    public ChooseGameScreen(double width, double height, String style, Controller controller) {
        gameTypeNames = ResourceBundle.getBundle("GameTypes");
        messages = ResourceBundle.getBundle("Messages");
        this.myRoot = new Group();
        this.myController = controller;

        myScene = new Scene(myRoot, width, height, Color.WHITE);
        myScene.getStylesheets().add(style);

        addFlowPane();
        createLoadHBox();

        Image img = new Image("welcome.jpg");
        ImagePattern pattern = new ImagePattern(img);
        myScene.setFill(pattern);

        myRoot.getChildren().addAll(myFlowPane, loadHBox);
    }
    public Scene getScene(){
        return myScene;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setId("button1");
        button.setOnAction(new ButtonHandler(text));
        return button;
    }

    class ButtonHandler implements EventHandler<ActionEvent> {
        private final String key;

        ButtonHandler(String gameName) {
            this.key = gameName;
        }
        @Override
        public void handle(ActionEvent event){
            try {
                System.out.print(key);
                myController.setGame(gameTypeNames.getString(key));
                myController.goToAddPlayersScreen();
            }
            catch (XmlReaderException e){
                String msg = e.getMessage();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("XML Config");
                alert.setHeaderText("XML Config File Error");
                alert.setContentText(msg);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
                System.exit(0);
            }
        }
    }

    private FlowPane addFlowPane() {
        myFlowPane = new FlowPane();
        myFlowPane.setVgap(Popup.HBOX_SPACING_FORTY);
        myFlowPane.setHgap(Popup.HBOX_SPACING_FORTY);
        myFlowPane.setLayoutX(Controller.WIDTH/ Popup.V_BOX_SPACING);
        myFlowPane.setLayoutY(Controller.HEIGHT/1.2);
        myFlowPane.setPrefWrapLength(Controller.WIDTH/Popup.V_BOX_SPACING);
        myFlowPane.setId("flowPane");
        gameTypeNamesKeys = gameTypeNames.getKeys();
        while (gameTypeNamesKeys.hasMoreElements()){
            myFlowPane.getChildren().add(createButton(gameTypeNamesKeys.nextElement()));
        }

        return myFlowPane;
    }


    private void createLoadHBox(){
        loadHBox = new HBox(Popup.HBOX_SPACING_TEN);

        loadButton = new Button(messages.getString("load"));
        loadButton.setOnAction(new LoadButtonHandler());

        goButton = new Button(messages.getString("go"));
        goButton.setDisable(true);
        goButton.setOnAction(new GoButtonHandler());

        chosenFile = new Label("");

        loadHBox.getChildren().addAll(loadButton,goButton,chosenFile);
        loadHBox.setLayoutX(Controller.WIDTH/5);
        loadHBox.setLayoutY(Controller.HEIGHT/Popup.VBOX_SPACING);
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
//            GameSaver<ClassicGame> mySaver = new GameSaver<ClassicGame>();
//            ClassicGame newGame = mySaver.reload(openedFile);
//            myController.setGame(newGame);
            SaveGame sg = new SaveGame();
            try {
                ClassicGame newGame = sg.load(openedFile.getAbsolutePath(), "Normal_Config_Rework.xml");
                myController.setGame(newGame);
                myController.startGame();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
