package View;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PropertySpaceDisplay extends SpaceDisplay {

    private Rectangle myRectangle;

    public PropertySpaceDisplay(String propName,String propPrice, String propColor){
        super(propName,propPrice,propColor);
    }

    @Override
    public Rectangle createBaseRectangle(int width, int height) {
        myRectangle = new Rectangle(width,height);
        myRectangle.setFill(Color.web(this.myPropColor));
        myRectangle.setStroke(Color.BLACK);

        return myRectangle;
    }


    public Pane createBottomPropColor(int width, int height){
        createBaseRectangle(width, height);
        HBox propColor = new HBox();
        propColor.setPrefWidth(myRectangle.getWidth());
        propColor.setPrefHeight(myRectangle.getHeight()/4);
        propColor.setId("propColor");
        propColor.setStyle(  " -fx-background-color: green");
        Pane imagePane = super.getMyPropertyPane();
        imagePane.getChildren().addAll(myRectangle,propColor);

        return imagePane;
    }


    public Pane createTopPropColor(int width, int height){
        createBaseRectangle(width, height);
        HBox propColor = new HBox();
        propColor.setPrefWidth(myRectangle.getWidth());
        propColor.setPrefHeight(myRectangle.getHeight()/4);
        propColor.setLayoutY(myRectangle.getHeight()-15);
        propColor.setId("propColor");
        propColor.setStyle(  " -fx-background-color: green");
        Pane imagePane = super.getMyPropertyPane();
        imagePane.getChildren().addAll(myRectangle,propColor);

        return imagePane;
    }


    public Pane createLeftPropColor(int width, int height){
        createBaseRectangle(width, height);
        HBox propColor = new HBox();
        propColor.setPrefHeight(myRectangle.getWidth());
        propColor.setPrefWidth(myRectangle.getHeight()/4);
        propColor.setLayoutX(myRectangle.getWidth()-15);
        propColor.setId("propColor");
        propColor.setStyle(  " -fx-background-color: green");
        Pane imagePane = super.getMyPropertyPane();
        imagePane.getChildren().addAll(myRectangle,propColor);



        return imagePane;
    }


    public Pane createRightPropColor(int width, int height){
        createBaseRectangle(width, height);
        HBox propColor = new HBox();
        propColor.setPrefHeight(myRectangle.getWidth());
        propColor.setPrefWidth(myRectangle.getHeight()/4);
        propColor.setId("propColor");
        propColor.setStyle(  " -fx-background-color: green");
        Pane imagePane = super.getMyPropertyPane();
        imagePane.getChildren().addAll(myRectangle,propColor);

        return imagePane;
    }



    @Override
    public Text createText(String propName, String price) {
        Text name = new Text(propName);
        return name;
    }

}
