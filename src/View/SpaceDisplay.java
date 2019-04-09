package View;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class SpaceDisplay {

    protected Pane myPropertyPane;
    protected String myPropName;
    protected String myPropPrice;
    protected String myPropColor;
    protected Rectangle myRectangle;

    public SpaceDisplay(String propName, String propPrice, String propColor){
        this.myPropName = propName;
        this.myPropPrice = propPrice;
        this.myPropColor = propColor;
        myPropertyPane = new Pane();
    }

    public SpaceDisplay(String propName, String propColor) {
        this.myPropName = propName;
        this.myPropColor = propColor;
        myPropertyPane = new Pane();
    }

    public Rectangle createBaseRectangle(int w, int h){
        myRectangle = new Rectangle(w,h);
        myRectangle.setFill(Color.web(this.myPropColor));
        myRectangle.setStroke(Color.BLACK);

        return myRectangle;
    };

    public abstract Text createText(String propName, String price);

    public Pane getMyPropertyPane(){
        return myPropertyPane;
    }



}
