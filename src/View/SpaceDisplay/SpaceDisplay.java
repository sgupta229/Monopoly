package View.SpaceDisplay;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class SpaceDisplay {

    protected String myPropName;
    protected String myBaseColor;
    protected String myImage;
    protected Rectangle myRectangle;
    protected StackPane myPropertyStackPane;
    protected Pane myImagePane;
    protected Pane myBoardPane;

    public SpaceDisplay(String propName, String baseColor, Pane boardPane, String image) {
        this.myPropName = propName;
        this.myBaseColor = baseColor;
        this.myBoardPane = boardPane;
        this.myImage = image;
        myPropertyStackPane = new StackPane();
        myPropertyStackPane.getChildren().addAll(createProp((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13), createText());
    }

    public SpaceDisplay(String baseColor, Pane boardPane, String image) {
        this.myBaseColor = baseColor;
        this.myBoardPane = boardPane;
        this.myImage = image;
        myPropertyStackPane = new StackPane();
        myPropertyStackPane.getChildren().addAll(createProp((int)myBoardPane.getPrefWidth()/13,(int)myBoardPane.getPrefWidth()/13));
    }

    public Pane createProp(int w, int h){
        myImagePane = new Pane();
        myRectangle = new Rectangle(w,h);
        myRectangle.setFill(Color.web(this.myBaseColor));
        myRectangle.setStroke(Color.BLACK);
        ImageView image = createImage();
        image.setFitWidth(w);
        image.setFitHeight(h);
        myImagePane.getChildren().addAll(myRectangle,image);

        return myImagePane;

    }

    public abstract ImageView createImage();

    public abstract Text createText();

    public StackPane getMyPropertyStackPane() {
        return myPropertyStackPane;
    }
}
