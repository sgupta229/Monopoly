package View;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
//TODO: refactor - probably would be smart to make an abstract GameScenes class that takes
//width, height, style, and Controller with methods getScene and setUpLayout

public class Layout {

    private Scene myScene;
    private Controller myController;
    private Group myRoot;
    private HBox myLayout;
    private Pane myBoardPane;
    private PlayerTabs myPlayersTabs;


    public Layout(double width, double height, String style, Controller controller){
        this.myController = controller;
        this.myRoot = new Group();

        myScene = new Scene(myRoot,width,height);
        myScene.getStylesheets().add(style);

        setUpLayout();
        myRoot.getChildren().addAll(myLayout);
    }
    public Scene getScene(){
        return myScene;
    }

    private void setUpLayout(){
        myLayout = new HBox();
        myLayout.setMaxSize(Controller.WIDTH, Controller.HEIGHT);
        myLayout.getChildren().addAll(createBoardPane(),createPlayersTabs());

    }

    private Pane createBoardPane(){
        myBoardPane = new Pane();
        myBoardPane.setPrefSize(Controller.WIDTH*0.67, Controller.HEIGHT);
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
//        myPlayerControlPane.setPrefSize(Controller.WIDTH/2.2, Controller.HEIGHT);
//        myPlayerControlPane.setId("playerControlPane");
//        myPlayerControlPane.getChildren().addAll(new PlayerTabs().getTabPane());
//        myLayout.getChildren().add(myPlayerControlPane);
        myPlayersTabs = new PlayerTabs();
        return myPlayersTabs.getTabPane();
    }


}
