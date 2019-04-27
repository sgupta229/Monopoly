package View;

import Controller.Controller;
import Model.AbstractPlayer;
import View.PopUps.Popup;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.ResourceBundle;


public class AddPlayersScreen {
    private ResourceBundle messages = ResourceBundle.getBundle("Messages");
    private Scene myScene;
    private double myWidth;
    private double myHeight;
    private Group myRoot;
    private Controller myController;
    transient private ObservableList<AbstractPlayer> myPlayers;
    transient private ObservableList<String> availableTokens;
    private AnchorPane anchorPane = new AnchorPane();

    private ComboBox myIconMenu;
    private TextField myPlayerNameField;

    public AddPlayersScreen(double width, double height, String style, Controller controller, ObservableList<AbstractPlayer> players) {
        this.myController = controller;
        this.myPlayers = players;
        this.availableTokens = FXCollections.observableList(myController.getGame().getPossibleTokens());
        this.myWidth = width;
        this.myHeight = height;
        this.myRoot = new Group();

        myScene = new Scene(myRoot,width,height);
        myScene.getStylesheets().add(style);

        setUpLayout();
        backButton();
        myRoot.getChildren().addAll(anchorPane, backButton());
    }

    public Scene getScene() {
        return myScene;
    }

    private Button backButton(){
        Button back = new Button("GO BACK");
        Stage newStage = new Stage();
        Controller newController = new Controller(newStage);
        back.setOnAction(e -> {
            newController.goToChooseGameScreen();
            myController.getStage().close();
        });
        return back;
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

        HBox bottomButtons = createBottomButtons();

        anchorPane.getChildren().addAll(title,screenContent,bottomButtons);
        AnchorPane.setTopAnchor(title,76.0);
        AnchorPane.setTopAnchor(screenContent,163.0);
        AnchorPane.setBottomAnchor(bottomButtons,60.0);
        AnchorPane.setRightAnchor(bottomButtons,60.0);
    }

    private HBox createBottomButtons(){
        HBox box = new HBox(Popup.HBOX_SPACING_FORTY);

        Button rules = new Button(messages.getString("edit-rules"));
        rules.setOnAction(e -> new RulesPopup(myController.getGame()).display());

        Button startGame = new Button(messages.getString("start-game"));
        startGame.setOnAction(new StartButtonHandler());
        startGame.disableProperty().bind(Bindings.size(myPlayers).lessThan(1));

        box.getChildren().addAll(rules,startGame);
        return box;
    }

    private VBox createNewPlayerBox(){
        VBox newPlayer = new VBox();
        newPlayer.setId("box");

        Text newPlayerTitle = new Text(messages.getString("new-player"));

        myIconMenu = createNewIconMenu();
        myPlayerNameField = createPlayerNameField();
        HBox nameAndIcon = new HBox(myIconMenu,myPlayerNameField);
        nameAndIcon.setAlignment(Pos.CENTER_LEFT);
        nameAndIcon.setSpacing(Popup.PADDING_TWENTY);

        ComboBox playerTypes = new ComboBox();
        playerTypes.setPromptText(messages.getString("choose-player-type"));
        playerTypes.setPrefWidth(350);
        Button add = new Button(messages.getString("add"));
        add.setAlignment(Pos.BOTTOM_RIGHT);
        add.setOnAction(new AddButtonHandler());
        add.disableProperty().bind(Bindings.size(myPlayers).greaterThan(7));

        newPlayer.getChildren().addAll(newPlayerTitle,playerTypes,nameAndIcon,add);
        return newPlayer;
    }

    private ComboBox createNewIconMenu(){
//        ComboBox icon = new ComboBox(makeImagesFromStrings(availableTokensStrings));
        ComboBox icon = new ComboBox(availableTokens);
        icon.setButtonCell(new ImageListCell());
        icon.setCellFactory(listView -> new ImageListCell());
        icon.setPrefSize(100,60);
        if(availableTokens.size()>=1) icon.setPromptText(availableTokens.get(0));
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

        playerList.setCellFactory((Callback<ListView<AbstractPlayer>, ListCell<AbstractPlayer>>) list -> new AbstractPlayerCell(availableTokens));

        editPlayerList.getChildren().addAll(editPlayerListTitle,playerList);
        return editPlayerList;
    }

    private class StartButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            myController.initializePlayers();
            myController.startGame();
        }
    }

    private class AddButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            try{String name = myPlayerNameField.getText();
            myPlayerNameField.clear();
            String icon = (String) myIconMenu.getValue();
            if (icon == null || name==null) return;
            myController.addPlayer(name,icon);
            System.out.println("added player");
            //remove icon from observablelist
            availableTokens.remove(myIconMenu.getValue());
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private class ImageListCell extends ListCell<String> {
        private ImageView view;

        ImageListCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            view = new ImageView();
        }

        @Override protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                view.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(item),
                        40.0,40.0,false,true));
                setGraphic(view);
            }
        }
    }


}
