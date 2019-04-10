package View.SpaceDisplay;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class LeftPropertyDisplay extends PropertyDisplay {

    public LeftPropertyDisplay(String propName, String propPrice, String propColor, Pane myBoard, String baseColor) {
        super(propName, propPrice, propColor, myBoard, baseColor);
        myPropStackPane.getChildren().addAll(createPropColor((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13), createText());
    }

    public LeftPropertyDisplay(Pane myBoard, String baseColor, String image) {
        super(myBoard, baseColor, image);
        myPropStackPane.getChildren().addAll(createImagePane((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefHeight(super.myRectangle.getWidth());
        propColor.setPrefWidth(super.myRectangle.getHeight()/4);
        propColor.setLayoutX(super.myRectangle.getWidth()-15);
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
