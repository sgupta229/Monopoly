package View.SpaceDisplay;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class BottomPropertyDisplay extends PropertyDisplay {

    public BottomPropertyDisplay(String propName, String propPrice, String propColor, String baseColor, int boardHeight, int boardDimen) {
        super(propName, propPrice, propColor,baseColor, boardHeight, boardDimen);
        myPropStackPane.getChildren().addAll(createPropColor(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen), createText());
    }

    public BottomPropertyDisplay(String baseColor, String image, int boardHeight, int boardDimen) {
        super(baseColor, image,boardHeight, boardDimen);
        myPropStackPane.getChildren().addAll(createImagePane(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefWidth(myRectangle.getWidth());
        propColor.setPrefHeight(myRectangle.getHeight()/PROP_SIZE);
    }

    @Override
    public void textLocation(FlowPane textPane) {
        textPane.setRotate(NO_ROTATION);
    }

    @Override
    public void imageLocation(Pane image) {
        image.setRotate(NO_ROTATION);
    }

}
