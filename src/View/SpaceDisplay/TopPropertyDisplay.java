package View.SpaceDisplay;

import Controller.Controller;
import View.Board;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class TopPropertyDisplay extends PropertyDisplay {

    public TopPropertyDisplay(String propName, String propPrice, String propColor,String baseColor, int boardHeight) {
        super(propName, propPrice, propColor,baseColor, boardHeight);
        myPropStackPane.getChildren().addAll(createPropColor(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen), createText());
    }

    public TopPropertyDisplay(String baseColor, String image, int boardHeight) {
        super(baseColor, image, boardHeight);
        myPropStackPane.getChildren().addAll(createImagePane(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefWidth(super.myRectangle.getWidth());
        propColor.setPrefHeight(super.myRectangle.getHeight()/4);
        propColor.setLayoutY(super.myRectangle.getHeight()-(myBoardDimen+1));
    }

    //-14 for classic,
    @Override
    public void imageLocation(Pane image) {
        image.setRotate(180);
    }

    @Override
    public void textLocation(FlowPane textPane) {
        textPane.setRotate(180);
    }

}
