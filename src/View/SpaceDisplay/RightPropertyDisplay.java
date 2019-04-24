package View.SpaceDisplay;

import Controller.Controller;
import View.Board;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RightPropertyDisplay extends PropertyDisplay {

    public RightPropertyDisplay(String propName,String propPrice, String propColor,String baseColor, int boardHeight){
        super(propName,propPrice,propColor,baseColor, boardHeight);
        myPropStackPane.getChildren().addAll(createPropColor(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen), createText());
    }

    public RightPropertyDisplay(String baseColor, String image, int boardHeight) {
        super(baseColor, image, boardHeight);
        myPropStackPane.getChildren().addAll(createImagePane(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen));
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
