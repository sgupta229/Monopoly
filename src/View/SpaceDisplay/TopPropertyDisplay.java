package View.SpaceDisplay;

import Controller.Controller;
import View.Board;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class TopPropertyDisplay extends PropertyDisplay {

    public TopPropertyDisplay(String propName, String propPrice, String propColor,String baseColor) {
        super(propName, propPrice, propColor,baseColor);
        myPropStackPane.getChildren().addAll(createPropColor(Board.BOARD_HEIGHT/13,Board.BOARD_HEIGHT/13), createText());

    }

    public TopPropertyDisplay(String baseColor, String image) {
        super(baseColor, image);
        myPropStackPane.getChildren().addAll(createImagePane(Board.BOARD_HEIGHT/13,Board.BOARD_HEIGHT/13));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefWidth(super.myRectangle.getWidth());
        propColor.setPrefHeight(super.myRectangle.getHeight()/4);
        propColor.setLayoutY(super.myRectangle.getHeight()-14);
    }

    @Override
    public void imageLocation(Pane image) {
        image.setRotate(180);
    }

    @Override
    public void textLocation(FlowPane textPane) {
        textPane.setRotate(180);
    }

}
