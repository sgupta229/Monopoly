package View;

import Model.properties.Property;
import View.PopUps.Popup;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class MiniPropertyRectangle {
    private Property myProperty;

    private StackPane myView;
    private Rectangle myRectangle;

    public MiniPropertyRectangle(Property prop){
        myProperty = prop;

        myRectangle = new Rectangle(Popup.HBOX_SPACING_TEN,Popup.HBOX_SPACING_TEN);

        myView = new StackPane();
        myView.getChildren().addAll(myRectangle);
    }

    public StackPane getView(){
        return myView;
    }
}
