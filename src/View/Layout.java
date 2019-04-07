package View;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Layout {

    private Group myRoot;
    private HBox myLayout;
    private Pane myBoardPane;
    private Pane myPlayerControlPane;


    public Layout(Group root){ //this will have the board/control type based on gametype selected
        this.myRoot = root;
        myLayout = new HBox();
        myLayout.setMaxSize(Main.WIDTH,Main.HEIGHT);
        createBoardPane(); //add board to constructor
        createPlayerControlPane(); //add controls to constructor
        myRoot.getChildren().addAll(myLayout);

    }

    private void createBoardPane(){
        myBoardPane = new Pane();
        myBoardPane.setPadding(new Insets(20,20,20,20));
        myBoardPane.setPrefSize(Main.WIDTH/1.5, Main.HEIGHT/1.8);
        myBoardPane.setId("boardPane");


        Board board = new Board(myBoardPane);
        Pane gameBoard = board.getGridPane();

        StackPane myGameBoard = new StackPane();
        ImageView logo = board.getLogo();
        myGameBoard.getChildren().addAll(gameBoard, logo);


        gameBoard.setPadding(new Insets(10,20,10,20));
        myBoardPane.getChildren().addAll(myGameBoard);

        myLayout.getChildren().add(myBoardPane);
    }


    private void createPlayerControlPane(){
        myPlayerControlPane = new Pane();
        myPlayerControlPane.setPrefSize(Main.WIDTH/2.2, Main.HEIGHT);
        myPlayerControlPane.setId("playerControlPane");
        myLayout.getChildren().add(myPlayerControlPane);

    }


}
