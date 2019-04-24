package View.SpaceDisplay;

import View.Board;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class BottomPropertyDisplay extends PropertyDisplay {

    public BottomPropertyDisplay(String propName, String propPrice, String propColor, String baseColor, int boardHeight) {
        super(propName, propPrice, propColor,baseColor, boardHeight);
        myPropStackPane.getChildren().addAll(createPropColor(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen), createText());
    }

    public BottomPropertyDisplay(String baseColor, String image, int boardHeight) {
        super(baseColor, image,boardHeight);
        myPropStackPane.getChildren().addAll(createImagePane(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen));
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
