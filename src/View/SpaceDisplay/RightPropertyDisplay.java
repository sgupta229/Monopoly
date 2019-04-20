package View.SpaceDisplay;

import Controller.Controller;
import View.Board;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RightPropertyDisplay extends PropertyDisplay {

    public RightPropertyDisplay(String propName,String propPrice, String propColor,String baseColor){
        super(propName,propPrice,propColor,baseColor);
        myPropStackPane.getChildren().addAll(createPropColor(Board.BOARD_HEIGHT/13,Board.BOARD_HEIGHT/13), createText());
    }

    public RightPropertyDisplay(String baseColor, String image) {
        super(baseColor, image);
        myPropStackPane.getChildren().addAll(createImagePane(Board.BOARD_HEIGHT/13,Board.BOARD_HEIGHT/13));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefHeight(super.myRectangle.getWidth());
        propColor.setPrefWidth(super.myRectangle.getHeight()/4);
    }

    @Override
    public void imageLocation(Pane image) {
        image.setRotate(-90);
    }

    @Override
    public void textLocation(FlowPane textPane) {
        textPane.setRotate(-90);
    }


}
