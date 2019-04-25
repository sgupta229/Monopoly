package View.SpaceDisplay;

import Controller.Controller;
import View.Board;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class LeftPropertyDisplay extends PropertyDisplay {

    public LeftPropertyDisplay(String propName, String propPrice, String propColor,String baseColor, int boardHeight, int boardDimen) {
        super(propName, propPrice, propColor, baseColor, boardHeight,boardDimen);
        myPropStackPane.getChildren().addAll(createPropColor(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen), createText());
    }

    public LeftPropertyDisplay(String baseColor, String image, int boardHeight,int boardDimen) {
        super(baseColor, image, boardHeight, boardDimen);
        myPropStackPane.getChildren().addAll(createImagePane(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefHeight(super.myRectangle.getWidth());
        propColor.setPrefWidth(super.myRectangle.getHeight()/4);
        propColor.setLayoutX(super.myRectangle.getWidth()-(super.myRectangle.getHeight()/4));
    }

    @Override
    public void imageLocation(Pane image){
        image.setRotate(90);
    }

    @Override
    public void textLocation(FlowPane textPane) {
        textPane.setRotate(90);
    }
}
