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

        Text title = new Text("Add Players");
        title.setWrappingWidth(myWidth);
        title.setId("header2");


        HBox screenContent = new HBox();
        screenContent.setPrefWidth(myWidth);
        screenContent.setAlignment(Pos.CENTER);
        screenContent.setPadding(new Insets(15, 15, 15, 15));
        screenContent.setSpacing(20);
        screenContent.setStyle("-fx-background-color: #937999;");


        VBox newPlayer = new VBox();
        newPlayer.setStyle("-fx-background-color: #aaaaaa;");
        Text newPlayerTitle = new Text("New Player");

        TextField playerName = new TextField();
        ComboBox playerTypes = new ComboBox();
        Button add = new Button("ADD");
        newPlayer.getChildren().addAll(newPlayerTitle,playerTypes,playerName,add);



        Text editPlayerListTitle = new Text("Edit Player List");
        VBox editPlayerList = new VBox();
        editPlayerList.setStyle("-fx-background-color: #a1aa5e;");
        ListView playerList = new ListView();
        editPlayerList.getChildren().addAll(editPlayerListTitle,playerList);


        screenContent.getChildren().addAll(newPlayer,editPlayerList);



        Button startGame = new Button("START GAME");

        anchorPane.getChildren().addAll(title,screenContent,startGame);
        anchorPane.setPrefSize(myWidth,myHeight);
        anchorPane.setStyle("-fx-background-color: #3fffff;");
        AnchorPane.setTopAnchor(title,76.0);
        AnchorPane.setTopAnchor(screenContent,163.0);
        AnchorPane.setBottomAnchor(startGame,60.0);
        AnchorPane.setRightAnchor(startGame,60.0);

        myRoot.getChildren().addAll(anchorPane);
    }

}
