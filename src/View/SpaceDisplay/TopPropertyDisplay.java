package View.SpaceDisplay;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class TopPropertyDisplay extends PropertyDisplay {

    public TopPropertyDisplay(String propName, String propPrice, String propColor, Pane myBoard, String baseColor) {
        super(propName, propPrice, propColor, myBoard, baseColor);
        myPropStackPane.getChildren().addAll(createPropColor((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13), createText());

    }

    public TopPropertyDisplay(Pane myBoard, String baseColor, String image) {
        super(myBoard, baseColor, image);
        myPropStackPane.getChildren().addAll(createImagePane((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefWidth(super.myRectangle.getWidth());
        propColor.setPrefHeight(super.myRectangle.getHeight()/4);
        propColor.setLayoutY(super.myRectangle.getHeight()-15);
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
