package View.SpaceDisplay;

import View.Board;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class BottomPropertyDisplay extends PropertyDisplay {


    public BottomPropertyDisplay(String propName, String propPrice, String propColor, String baseColor) {
        super(propName, propPrice, propColor,baseColor);
        myPropStackPane.getChildren().addAll(createPropColor(Board.BOARD_HEIGHT/13,Board.BOARD_HEIGHT/13), createText());
    }

    public BottomPropertyDisplay(String baseColor, String image) {
        super(baseColor, image);
        myPropStackPane.getChildren().addAll(createImagePane(Board.BOARD_HEIGHT/13,Board.BOARD_HEIGHT/13));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefWidth(myRectangle.getWidth());
        propColor.setPrefHeight(myRectangle.getHeight()/4);
    }

    @Override
    public void textLocation(FlowPane textPane) {
        textPane.setRotate(0);
    }

    @Override
    public void imageLocation(Pane image) {
        image.setRotate(0);
    }

}
