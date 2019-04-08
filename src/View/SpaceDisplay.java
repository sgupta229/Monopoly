package View;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class SpaceDisplay {

    protected Pane myPropertyPane;
    protected String myPropName;
    protected String myPropPrice;
    protected String myPropColor;

    public SpaceDisplay(String propName, String propPrice, String propColor){
        this.myPropName = propName;
        this.myPropPrice = propPrice;
        this.myPropColor = propColor;
        myPropertyPane = new Pane();
    }

    public abstract Rectangle createImage(int w, int h);

    public abstract Text createText(Text propName, Text price);

    public Pane getMyPropertyPane(){
        return myPropertyPane;
    }



}
