package View;

import javafx.geometry.Insets;
import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Layout {

    private Group myRoot;
    private HBox myLayout;
    private Pane myBoardPane;
    private PlayersTabs myPlayersTabs;


    public Layout(Group root){ //this will have the board/control type based on gametype selected
        this.myRoot = root;
        myLayout = new HBox();
        myLayout.setMaxSize(Main.WIDTH,Main.HEIGHT);
        myLayout.getChildren().addAll(createBoardPane(),createPlayersTabs());
        myRoot.getChildren().clear();
        myRoot.getChildren().addAll(myLayout);
    }

    private Pane createBoardPane(){
        myBoardPane = new Pane();
//        myBoardPane.setPadding(new Insets(20,20,20,20));
        myBoardPane.setPrefSize(Main.WIDTH*0.67, Main.HEIGHT);
        myBoardPane.setId("boardPane");


        Board board = new Board(myBoardPane);
        Pane gameBoard = board.getGridPane();

        StackPane myGameBoard = new StackPane();
        ImageView logo = board.getLogo();
        myGameBoard.getChildren().addAll(gameBoard, logo);


        gameBoard.setPadding(new Insets(10,20,10,20));
        myBoardPane.getChildren().addAll(myGameBoard);

        return myBoardPane;
    }


    private TabPane createPlayersTabs(){
//        myPlayerControlPane = new Pane();
//        myPlayerControlPane.setPrefSize(Main.WIDTH/2.2, Main.HEIGHT);
//        myPlayerControlPane.setId("playerControlPane");
//        myPlayerControlPane.getChildren().addAll(new PlayersTabs().getTabPane());
//        myLayout.getChildren().add(myPlayerControlPane);
        myPlayersTabs = new PlayersTabs();
        return myPlayersTabs.getTabPane();
    }


}
