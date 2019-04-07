package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Board {

    private static final String BOARD_PATH = "classic.jpg";
    private Pane myBoardPane;
    private TilePane myTopPane;
    private TilePane myLeftPane;
    private TilePane myRightPane;
    private TilePane myBottomPane;
    private GridPane myGridPane;

    private ImageView boardLogo;



    public Board(Pane board){
        this.myBoardPane = board;

        myGridPane = new GridPane();
        myGridPane.setGridLinesVisible(true);

//        myTopPane = new TilePane();
//        myTopPane.setMaxWidth(myBoardPane.getPrefWidth());
//        myTopPane.setMaxHeight(myBoardPane.getPrefHeight()/12);
//
//        myLeftPane = new TilePane();
//        myLeftPane.setMaxWidth(myBoardPane.getPrefWidth()/12);
//        myLeftPane.setMaxHeight(myBoardPane.getPrefHeight());
//
//        myRightPane = new TilePane();
//        myRightPane.setMaxWidth(myBoardPane.getPrefWidth()/12);
//        myRightPane.setMaxHeight(myBoardPane.getPrefHeight());
//
//        myBottomPane = new TilePane();
//        myBottomPane.setMaxWidth(myBoardPane.getPrefWidth());
//        myBottomPane.setMaxHeight(myBoardPane.getPrefHeight()/12);
//
////        myInnerPane.setPadding(new Insets(20,20,20,20));
//        myTopPane.setId("innerBoard");
//        myLeftPane.setId("innerBoard");
//        myRightPane.setId("innerBoard");
//        myBottomPane.setId("innerBoard");

        final int numCols = 11 ;
        final int numRows = 11 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();


            colConst.setPercentWidth(myBoardPane.getPrefHeight() / numCols);
            myGridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(myBoardPane.getPrefHeight() / numRows);
            myGridPane.getRowConstraints().add(rowConst);
        }

        myGridPane.add(createImage((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13), 0,0);


        Button popUpExample = new Button("Click ");
        Button popUpExample2 = new Button("Click");
        Button popUpExample3 = new Button("Click ");
        Button popUpExample4 = new Button("Click ");
        Button popUpExample5 = new Button("Click ");


        var logo = new Image(this.getClass().getClassLoader().getResourceAsStream(BOARD_PATH));
        boardLogo = new ImageView(logo);


        //myGridPane.add(logo1,1,1);
        ArrayList<Node> spaces = new ArrayList<>();
        ArrayList<Node> spacesLeft = new ArrayList<>();
        ArrayList<Node> spacesRight = new ArrayList<>();
        ArrayList<Node> spacesBottom = new ArrayList<>();

//
//
//        for (int i=0; i<9; i++){
//            spaces.add(createImage((int)(myBoardPane.getPrefWidth())/10,(int)(myBoardPane.getPrefWidth())/10));
//        }
//        for (Node item : spaces) {
//            myTopPane.getChildren().add(item);
//        }
//
//
//
//        for (int i=0; i<9; i++){
//            spacesRight.add(createImage((int)(myBoardPane.getPrefWidth())/10,(int)(myBoardPane.getPrefWidth())/10));
//        }
//        for (Node item : spacesRight) {
//            myRightPane.getChildren().add(item);
//        }
//
//
//
//
//        for (int i=0; i<9; i++){
//            spacesLeft.add(createImage((int)(myBoardPane.getPrefWidth())/10,(int)(myBoardPane.getPrefWidth())/10));
//        }
//        for (Node item : spacesLeft) {
//            myLeftPane.getChildren().add(item);
//        }
//
//
//        for (int i=0; i<9; i++){
//            spacesBottom.add(createImage((int)(myBoardPane.getPrefWidth())/10,(int)(myBoardPane.getPrefWidth())/10));
//        }
//        for (Node item : spacesBottom) {
//            myBottomPane.getChildren().add(item);
//        }







        Popup myPopup = new BuyPropertyPopup("Property", "Would you like to purchase this property?");
        popUpExample.setOnAction(e -> myPopup.display());



    }

    public Pane getMyTopPane(){
        return myTopPane;
    }


    public Pane getMyLeftPane(){
        return myLeftPane;
    }

    public Pane getMyRightPane(){
        return myRightPane;
    }
    public Pane getMyBottomPane(){
        return myBottomPane;
    }

    public ImageView getLogo(){
        return boardLogo;

    }

    public Pane getGridPane(){
        return myGridPane;

    }

    protected Pane createImage(int w, int h) {
        Rectangle rectangle = new Rectangle(w,h);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        Rectangle propColor = new Rectangle(rectangle.getWidth(), rectangle.getHeight()/5);
        propColor.setStroke(Color.BLACK);
        propColor.setFill(Color.ALICEBLUE);
        Pane imagePane = new Pane( rectangle, propColor );

        return imagePane;
    }

}