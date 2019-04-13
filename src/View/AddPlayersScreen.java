package View;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AddPlayersScreen {
    private ResourceBundle messages = ResourceBundle.getBundle("Messages");
    private Scene myScene;
    private double myWidth;
    private double myHeight;
    private Group myRoot;
    private Controller myController;
    private ObservableList<AbstractPlayer> myPlayers;
    private ObservableList<String> availableTokensStrings;
//    private ObservableList<Image> availableTokensImages;
    private AnchorPane anchorPane = new AnchorPane();

    private ComboBox myIconMenu;
    private TextField myPlayerNameField;

    public AddPlayersScreen(double width, double height, String style, Controller controller, ObservableList<AbstractPlayer> players, ObservableList<String> tokens) {
        this.myController = controller;
        this.myPlayers = players;
        this.availableTokensStrings = tokens;
//        this.availableTokensImages = makeImagesFromStrings(availableTokensStrings);
//        this.availableTokensStrings.addListener(availableTokensImages);
        this.myWidth = width;
        this.myHeight = height;
        this.myRoot = new Group();

        myScene = new Scene(myRoot,width,height);
        myScene.getStylesheets().add(style);

        setUpLayout();

        myRoot.getChildren().addAll(anchorPane);
    }

    public Scene getScene() {
        return myScene;
    }

    private void setUpLayout(){
        anchorPane.setPrefSize(myWidth,myHeight);

        Text title = new Text(messages.getString("add-players"));
        title.setWrappingWidth(myWidth);
        title.setId("header2");

        HBox screenContent = new HBox();
        screenContent.setPrefWidth(myWidth);
        screenContent.setAlignment(Pos.CENTER);
        screenContent.setId("box");

        screenContent.getChildren().addAll(createNewPlayerBox(),createEditPlayerListBox());

        Button startGame = new Button(messages.getString("start-game"));
        startGame.setOnAction(new StartButtonHandler());

        anchorPane.getChildren().addAll(title,screenContent,startGame);
        AnchorPane.setTopAnchor(title,76.0);
        AnchorPane.setTopAnchor(screenContent,163.0);
        AnchorPane.setBottomAnchor(startGame,60.0);
        AnchorPane.setRightAnchor(startGame,60.0);
    }

    //TODO: refactor
    //TODO: add dynamic ness
    private VBox createNewPlayerBox(){
        VBox newPlayer = new VBox();
        newPlayer.setId("box");

        Text newPlayerTitle = new Text(messages.getString("new-player"));

        myIconMenu = createNewIconMenu();
        myPlayerNameField = createPlayerNameField();
        HBox nameAndIcon = new HBox(myIconMenu,myPlayerNameField);
        nameAndIcon.setSpacing(20);

        ComboBox playerTypes = new ComboBox();
        playerTypes.setPromptText(messages.getString("choose-player-type"));
        playerTypes.setPrefWidth(300);
        Button add = new Button(messages.getString("add"));
        add.setAlignment(Pos.BOTTOM_RIGHT);
        add.setOnAction(new AddButtonHandler());

        newPlayer.getChildren().addAll(newPlayerTitle,playerTypes,nameAndIcon,add);
        return newPlayer;
    }

    private ComboBox createNewIconMenu(){
        ComboBox icon = new ComboBox(makeImagesFromStrings(availableTokensStrings));
        icon.setButtonCell(new ImageListCell());
        icon.setCellFactory(listView -> new ImageListCell());
        icon.setPrefSize(100,60);
        return icon;
    }

    private TextField createPlayerNameField(){
        TextField playerName = new TextField();
        playerName.setPrefWidth(300);
        playerName.setPromptText(messages.getString("name-of-player"));
        return playerName;
    }

    private VBox createEditPlayerListBox(){
        VBox editPlayerList = new VBox();
        editPlayerList.setId("box");

        Text editPlayerListTitle = new Text(messages.getString("edit-player-list"));
        ListView playerList = new ListView(myPlayers);
        playerList.setMaxHeight(180.0);

        editPlayerList.getChildren().addAll(editPlayerListTitle,playerList);
        return editPlayerList;
    }

    private ObservableList<Image> makeImagesFromStrings(ObservableList<String> strings){
        List<Image> images = new ArrayList<>();
        for(String s:strings){
            images.add(new Image(this.getClass().getClassLoader().getResourceAsStream(s),
                    40,40,false,false));
        }
        return FXCollections.observableList(images);
    }

    class StartButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            myController.startGame();
        }
    }

    class AddButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            String name = myPlayerNameField.getText();
            myPlayerNameField.clear();
            Image icon = (Image) myIconMenu.getValue();
            //remove icon from observablelist
            //reset combobox
            myController.addPlayer(name,icon);
            System.out.println("added player");
        }
    }


    class ImageListCell extends ListCell<Image> {
        private final ImageView view;

        ImageListCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            view = new ImageView();
        }

        @Override protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                view.setImage(item);
                setGraphic(view);
            }
        }
    }

}
