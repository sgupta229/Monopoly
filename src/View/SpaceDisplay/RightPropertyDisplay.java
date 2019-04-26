package View.SpaceDisplay;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RightPropertyDisplay extends PropertyDisplay {

    public RightPropertyDisplay(String propName,String propPrice, String propColor,String baseColor, int boardHeight, int boardDimen){
        super(propName,propPrice,propColor,baseColor, boardHeight, boardDimen);
        myPropStackPane.getChildren().addAll(createPropColor(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen), createText());
    }

    public RightPropertyDisplay(String baseColor, String image, int boardHeight, int boardDimen) {
        super(baseColor, image, boardHeight, boardDimen);
        myPropStackPane.getChildren().addAll(createImagePane(myBoardHeight/myBoardDimen,myBoardHeight/myBoardDimen));
    }

    @Override
    public void propColorLocation(HBox propColor) {
        propColor.setPrefHeight(super.myRectangle.getWidth());
        propColor.setPrefWidth(super.myRectangle.getHeight()/PROP_SIZE);
    }

    @Override
    public void imageLocation(Pane image) {
        image.setRotate(-ROTATION_90);
    }

    @Override
    public void textLocation(FlowPane textPane) {
        textPane.setRotate(-ROTATION_90);
    }


}
