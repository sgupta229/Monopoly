package View.SpaceDisplay;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class BottomPropertyDisplay extends PropertyDisplay {

    public BottomPropertyDisplay(String propName, String propPrice, String propColor, Pane myBoard, String baseColor) {
        super(propName, propPrice, propColor, myBoard, baseColor);
        myPropStackPane.getChildren().addAll(createPropColor((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13), createText());
    }

    public BottomPropertyDisplay(Pane myBoard, String baseColor, String image) {
        super(myBoard, baseColor, image);
        myPropStackPane.getChildren().addAll(createImagePane((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13));
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
