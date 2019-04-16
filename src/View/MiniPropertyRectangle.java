package View;

import Model.properties.Property;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class MiniPropertyRectangle {
    private Property myProperty;

    private StackPane myView;
    private Rectangle myRectangle;

    public MiniPropertyRectangle(Property prop){
        myProperty = prop;

        myRectangle = new Rectangle(10,10);

        myView = new StackPane();
        myView.getChildren().addAll(myRectangle);
    }

    public StackPane getView(){
        return myView;
    }
}
