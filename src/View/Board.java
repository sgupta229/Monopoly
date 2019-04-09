package View;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Board {

    private static final String BOARD_PATH = "classic.jpg";
    private Pane myBoardPane;
    private GridPane myGridPane;
    private ImageView boardLogo;



    public Board(Pane board){
        this.myBoardPane = board;
        myGridPane = new GridPane();
        myGridPane.setGridLinesVisible(true);

        


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


        for (int i=1; i<10; i++){
            PropertySpaceDisplay propSpaces = new PropertySpaceDisplay("Conn", "3", "#c7edc9");
            Pane prop = propSpaces.createRightPropColor((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13);
            Button popUpExample = new Button("1 ");

            Popup myPopup = new BuyPropertyPopup("Property", "Would you like to purchase this property?");
            popUpExample.setOnAction(e -> myPopup.display());
            prop.getChildren().add(popUpExample);
            myGridPane.add(prop, 10,i);

        }
        for (int i=1; i<10; i++){
            PropertySpaceDisplay propSpaces = new PropertySpaceDisplay("Conn", "3", "#c7edc9");
            Pane prop = propSpaces.createLeftPropColor((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13);
            myGridPane.add(prop, 0,i);
        }
        for (int i=1; i<10; i++){
            PropertySpaceDisplay propSpaces = new PropertySpaceDisplay("Conn", "3", "#c7edc9");
            Pane prop = propSpaces.createBottomPropColor((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13);
            myGridPane.add(prop, i,10);
        }

        for (int i=1; i<10; i++){
            PropertySpaceDisplay propSpaces = new PropertySpaceDisplay("Conn", "3", "#c7edc9");
            Pane prop = propSpaces.createTopPropColor((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13);
            myGridPane.add(prop, i,0);
        }

        for (int i=0; i<11; i+=10){
            CornerSpaceDisplay propSpaces = new CornerSpaceDisplay("GO","#c7edc9");
            Pane prop;
            if (i==10){
                prop = propSpaces.createGoSpaces((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13);

                myGridPane.add(prop, i,i);

            }
            else{
                prop = propSpaces.createFreeParkingSpaces((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13);

                myGridPane.add(prop, i,i);

            }
        }



        CornerSpaceDisplay propSpaces = new CornerSpaceDisplay("GO","#c7edc9");
        Pane prop = propSpaces.createGoToJailSpaces((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13);
        myGridPane.add(prop, 10,0);
        CornerSpaceDisplay propSpaces2 = new CornerSpaceDisplay("GO","#c7edc9");
        Pane prop2 = propSpaces2.createInJailSpaces((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13);
        myGridPane.add(prop2, 0,10);







        var logo = new Image(this.getClass().getClassLoader().getResourceAsStream(BOARD_PATH));
        boardLogo = new ImageView(logo);
        boardLogo.setFitWidth((myBoardPane.getPrefWidth()/13)*9);
        boardLogo.setFitHeight((myBoardPane.getPrefWidth()/13)*9);
        boardLogo.setId("boardLogo");




    }

    public ImageView getLogo(){ return boardLogo; }

    public Pane getGridPane(){ return myGridPane; }


}
