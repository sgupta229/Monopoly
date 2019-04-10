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

public class Layout {

    private Scene myScene;
    private Controller myController;
    private Group myRoot;
    private HBox myLayout;
    private Pane myBoardPane;
    private Board myBoard;
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


        myBoard = new Board(myBoardPane,myController);
        Pane gameBoard = myBoard.getGridPane();

        StackPane myGameBoard = new StackPane();
        ImageView logo = myBoard.getLogo();
        myGameBoard.getChildren().addAll(gameBoard, logo);


        gameBoard.setPadding(new Insets(10,20,10,20));
        myBoardPane.getChildren().addAll(myGameBoard);

        return myBoardPane;
    }


    private TabPane createPlayersTabs(){
        myPlayersTabs = new PlayerTabs(myController,myBoard);
        return myPlayersTabs.getTabPane();
    }


}
