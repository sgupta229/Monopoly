package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.awt.*;


public class AddPlayersScreen {
    private double myWidth;
    private double myHeight;
    private Group myRoot;
    private AnchorPane anchorPane = new AnchorPane();

    public AddPlayersScreen(Group root, double screenWidth, double screenHeight) {
        this.myWidth = screenWidth;
        this.myHeight = screenHeight;
        this.myRoot = root;

        anchorPane.setPrefSize(myWidth,myHeight);
        anchorPane.setStyle("-fx-background-color: #3fffff;");

        Text title = new Text("Add Players");
        title.setWrappingWidth(myWidth);
        title.setId("header2");


        HBox screenContent = new HBox();
        screenContent.setPrefWidth(myWidth);
        screenContent.setAlignment(Pos.CENTER);
        screenContent.setPadding(new Insets(15, 15, 15, 15));
        screenContent.setSpacing(20);
        screenContent.setStyle("-fx-background-color: #937999;");

        VBox newPlayer = createNewPlayerBox();
        VBox editPlayerList = createEditPlayerListBox();
        screenContent.getChildren().addAll(newPlayer,editPlayerList);

        Button startGame = new Button("START GAME");

        anchorPane.getChildren().addAll(title,screenContent,startGame);
        AnchorPane.setTopAnchor(title,76.0);
        AnchorPane.setTopAnchor(screenContent,163.0);
        AnchorPane.setBottomAnchor(startGame,60.0);
        AnchorPane.setRightAnchor(startGame,60.0);

        myRoot.getChildren().addAll(anchorPane);
    }

    private VBox createNewPlayerBox(){
        VBox newPlayer = new VBox();
        newPlayer.setStyle("-fx-background-color: #aaaaaa;");
        newPlayer.setPadding(new Insets(15, 15, 15, 15));
        newPlayer.setSpacing(20);

        Text newPlayerTitle = new Text("New Player");

        ComboBox icon = new ComboBox();
        icon.setPrefSize(100,60);
        TextField playerName = new TextField();
        playerName.setPrefWidth(300);
        playerName.setPromptText("Name of Player");
        HBox nameAndIcon = new HBox(icon,playerName);
        nameAndIcon.setSpacing(20);

        ComboBox playerTypes = new ComboBox();
        playerTypes.setPromptText("Choose Player Type");
        playerTypes.setPrefWidth(300);
        Button add = new Button("ADD");
        add.setAlignment(Pos.BOTTOM_RIGHT);

        newPlayer.getChildren().addAll(newPlayerTitle,playerTypes,nameAndIcon,add);
        return newPlayer;
    }

    private VBox createEditPlayerListBox(){
        VBox editPlayerList = new VBox();
        editPlayerList.setStyle("-fx-background-color: #a1aa5e;");
        editPlayerList.setPadding(new Insets(15, 15, 15, 15));
        editPlayerList.setSpacing(20);

        Text editPlayerListTitle = new Text("Edit Player List");
        ListView playerList = new ListView();
        playerList.setMaxHeight(180.0);

        editPlayerList.getChildren().addAll(editPlayerListTitle,playerList);
        return editPlayerList;
    }

}
